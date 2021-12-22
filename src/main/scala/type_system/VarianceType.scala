object VarianceType {
  // let's assume the following type definitions:
  trait Item { def productNumber: String }
  trait Buyable extends Item { def price: Int }
  trait Book extends Buyable { def isbn: String }

  // let's also assume the following parameterized types:
  trait Pipeline[T]: // Pipeline is invariant in their type param 
                    // -> Pipeline[Item], Pipeline[Buyable], Pipeline[Book] are in `no subtyping rela` to each other
    def process(t: T): T

  // an example of a covariant type
  trait Producer[+T]:
    def make: T

  // an example of a contravariant type
  trait Consumer[-T]:
    def take(t: T): Unit
    
  object Invariant:
    // Assume this method consumes 2 values of type Pipeline[Buyable], and passes `b` 
    def oneOf(
      p1: Pipeline[Buyable],
      p2: Pipeline[Buyable],
      b: Buyable
    ): Buyable =
      val b1 = p1.process(b)
      val b2 = p2.process(b)
      if b1.price < b2.price then b1 else b2
    // 1. We cannot pass Pipeline[Book] to this method..
    // .. bc we are calling p1 and p2 with a value of type Buyable..
    // .. A Pipeline[Book] expects a Book --> potentially causes RT-e
    // 2. We cannot pass Pipeline[Item] ..
    // .. bc calling process on it only promises to return an `Item`
    // .. however, we are supposed to return a Buyable
    
    /* Remarks:
      Type Pipeline needs to be invariant because:
        **it uses its type parameter `T` BOTH as an argument AND as a return type**
    */
  end Invariant

  object Covariant:
    /* Remarks:
      Type Producer is valid to be covariant because:
        **it uses its type parameter `T` as an argument**
    */
    // As Producer is covariant --> we can pass (or return) Producer[Book] where a Producer[Buyable] is expected..
    // .. bc `Producer[Buyable].make` only promises to return a `Buyable`
    // The caller of `make` will be happy to also accept a `Book`, which is SUBTYPE of `Buyable` 
    // - that is, at least a Buyable

    def makeTwo(p: Producer[Buyable]): Int = // expect a producer of buyable
      p.make.price + p.make.price

    // perfectly fine to pass a producer for book
    val bookProducer: Producer[Book] = ???
    makeTwo(bookProducer)

    /**
     * Remarks
     * Covariant is encountered a lot when dealing with immutable containers, i.e. List, Seq...
     */
  end Covariant

  object Contravariant:
    /* Remarks:
      Type Consumer is valid to be contravariant because:
        **it uses its type parameter `T` as an argument position**
    */
    // Consumer is contravariant --> we can pass (or return) Consumer[Item] where a Consumer[Buyable] is expected..
    // .. bc `Consumer[Item].take` accepts an Item
    // The caller of `take` can also supply a `Buyable` since `Buyable` is a subtype of `Item`, 
    // .. which will be happily accepted by `Consumer[Item]`
    // - that is, at least an `Item`

    // use cases:
    trait Function[-A, +B]: // consumes values of type A, but produces values of type B
      def apply(a: A): B

    val f: Function[Buyable, Buyable] = b => b

    // OK to return a Buyable where a Item is expected
    val g: Function[Buyable, Item] = f

    // OK to provide a Book where a Buyable is expected
    val h: Function[Book, Buyable] = f

}