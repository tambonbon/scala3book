object ImplicitConversion {
  // use given instance of the scala.Conversion class
  given Conversion[String, Int] with
    def apply(s: String): Int = Integer.parseInt(s)

  // using an alias this can be expressed more concisely as
  given Conversion[String, Int] = Integer.parseInt(_)
}
