object ContextBounds {
  // For example, this `maximum` takes a **context param** of type `Ord`, only to pass it on as an argument to `max`
  // def maximum[A](xs: List[A])(using ord: Ord[A]): A = 
  //   xs.reduceLeft(max(ord))
  // // actually, the param name `ord` is NOT required, so:
  // def maximumNoName[A](xs: List[A])(using Ord[A]): A = xs.reduceLeft(max)
  // // a **context bound* is a shorthand for:
  // // "a context param that depends on a type param"
  // def maximum[A: Ord](xs: List[A]): A = xs.reduceLeft(max)



  // case class Ord[T]()
  // def max[A](ord: Ord[A]) = ???
}
