object TypeClasses {
  /**
   * In Scala 3, type classes are just traits with one or more parameters..
   * .. whose implementations are provided by given instances.
   */

  //  1st step: declare a parameterized trait having one or more abstract method
  // (this is also a typeclass)
  trait Showable[A]:
    extension(a: A) def show: String

  // this is VERY DIFFERENT from a normal trait
  trait Show:
    def show: String
  
  /**
   * 1. Typeclass like `Showable` takes a type param A, while `Show` do not
   * 2. To add the `show` functionality to a certain type `A` ..
   *  - normal trait requires `A extends Show`
   *  - typeclass requires to have an implementation of `Showable[A]`
   * 3. To allow the same method calling syntax in both `Showable` mimicking the one of `Show` ..
   *  .. we define `Showable.show` as an extension method
   */

  // 2nd step: determine what classes `Showable` should work for, then implement that behaviour for them
  // e.g. if we wanna implement `Showable` for this `Person` ..
  case class Person(firstName: String, lastName: String)
  // .. you'll define a `given` value for `Showable[Person]`..
  // .. this provides a concrete instance of `Showable` for `Person` class
  given Showable[Person] with
    extension(p: Person) def show: String = 
      s"${p.firstName} ${p.lastName}"

  // 3rd, use the type class
  val person = Person("John", "doe")
  println(person.show)

  // Writing methods that use the typeclass
  def showAll[S: Showable](xs: List[S]): Unit = 
    xs.foreach(x => println(x.show)) // x: S

  //  typeclass with multiple methods
  trait HasLegs[A]:
    extension (a: A)
      def walk(): Unit
      def run(): Unit

}
