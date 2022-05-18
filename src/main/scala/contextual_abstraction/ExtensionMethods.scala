object ExtensionMethods {
  // Extension methods let you add methods to a type after the type is defined
  // (sounds like decorator pattern)

  // Example: Assume someone defined a Circle class
  case class Circle(x: Double, y: Double, radius: Double)

  // you wanna make a circleCircumference method...

  // Method 1:
  object CircleHelpers {
    def circumference(c: Circle): Double = c.radius * 2 * math.Pi
  }
  // to use:
  val aCircle = Circle(2,3,5)
  CircleHelpers.circumference(aCircle)

  // Method 2: (with extension)
  extension (c: Circle) // nb: the type in extension will be the type to be extended
    def circumference: Double = c.radius * 2 * math.Pi
  // to use
  aCircle.circumference
}
