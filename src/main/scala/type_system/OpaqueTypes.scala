// Scala 3 Opaque type aliases provide type abstractions without any overhead.
object OpaqueTypes {

  object AbstractionOverhead:
    class Logarithm(protected val underlying: Double):
      def toDouble: Double = math.exp(underlying)
      def + (that: Logarithm): Logarithm =
        // here we use the apply method on the companion
        Logarithm(this.toDouble + that.toDouble)
      def * (that: Logarithm): Logarithm =
        new Logarithm(this.underlying + that.underlying)

    object Logarithm:
      def apply(d: Double): Logarithm = new Logarithm(math.log(d))

    @main def overhead(): Unit =
      val L2 = Logarithm(2.0)
      val L3 = Logarithm(3.0)
      println((L2 * L3).toDouble) // print 6.0
    // Logarithm imposes severe performance overhead:
    // For every single math op, we need to extract the underlying value..
    // .. then wrap it again in a new instance of Logarithm
  end AbstractionOverhead

  object ModuleAbstractions:
    // Instead of defining Logarithm as a class, we define it using a type alias
    trait Logarithms:
      type Logarithm

      // operations on Loga
      def add(x: Logarithm, y: Logarithm): Logarithm
      def mul(x: Logarithm, y: Logarithm): Logarithm

      // funcitons to convert between Double and Loga
      def make(d: Double): Logarithm
      def extract(x: Logarithm): Double

      // extension methods to use `add` and `mul` as "methods" on Logarithm
      extension (x: Logarithm)
        def toDouble: Double = extract(x)
        def + (y: Logarithm): Logarithm = add(x,y)
        def * (y: Logarithm): Logarithm = mul(x,y)
    
    // let's implement this abstract interf by saying type Logarithm is equal to Double
    object LogarithmsImpl extends Logarithms:
      type Logarithm = Double

      // operations on Logarithm
      def add(x: Logarithm, y: Logarithm): Logarithm = make(x.toDouble + y.toDouble)
      def mul(x: Logarithm, y: Logarithm): Logarithm = x + y

      // functions to convert between Double and Logarithm
      def make(d: Double): Logarithm = math.log(d)
      def extract(x: Logarithm): Double = math.exp(x)

    /**
     * Remarks
     * - Leaky abstractions: Have to make sure to only lever program against the abstract interf `Logarithms` 
     * .. and NEVER directly use `LogaImpl`
     * - Lots of effort just to hide impl detail of Logarithm
     */

    import LogarithmsImpl.*
    @main def ModuleAbstr(): Unit = {
      val L: Logarithm = make(1.0)
      val d: Double = L // type checks AND leaks the equality!
    }
  end ModuleAbstractions

  object OpaqueType:
    // Instead of manually splitting our Loga into an abstract part ++ into a concrete impl..
    // --> use opaque types
    object Logarithms:
      opaque type Logarithm = Double

      object Logarithm:
        def apply(d: Double): Logarithm = math.log(d)

      extension (x: Logarithm)
        def toDouble: Double = math.exp(x)
        def + (y: Logarithm): Logarithm = Logarithm(math.exp(x) + math.exp(y))
        def * (y: Logarithm): Logarithm = x + y

    /**
     * Remarks
     * - The fact that Logarithm is defined as Double is only known in the scope where Logarithm is defined..
     * .. which corresponds to the object `Logarithms`
     * - Outside of module, the type `Logarithm` is completely encapsulate (or opaque)
     */
    import Logarithms.*
    @main def Opaque: Unit = {
      val L2 = Logarithm(2.0)
      val L3 = Logarithm(3.0)
      println((L2*L3).toDouble)

      // val D: Double = L2 // Type mismatch error (Found Loga, required Double)
    }
  /**
   * Remarks
   * Opaque types offer a sound abstraction over implementation details, without imposing performance overhead.
   * As illustrated above, opaque types are convenient to use, and integrate very well with the Extension Methods feature.
   */
}
