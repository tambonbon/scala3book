package type_system

object IntersectionTypes {
  
  trait Resettable:
    def reset(): Unit

  trait Growable[A]:
    def add(a: A): Unit

  def f(x: Resettable & Growable[String]): Unit = // x is required to be both a Resettable and a Growable[String]
    x.reset()
    x.add("first")
}
