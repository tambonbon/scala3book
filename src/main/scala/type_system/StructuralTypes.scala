object StructuralTypes {
  // Structural types help when you like to support simple dot notation in dynamic contexts..
  // .. w/o losing the advantages of static typing

  class Record(elems: (String, Any)*) extends Selectable:
    private val fields: elems.toMap
    def selectDynamic(name: String): Any = fields(name)

  type Person = Record {
    val name: String
    val age: Int
  }
  // The Person type adds a "refinement" to its parent type "Record", defining name and age
  // That "refinement" --> structural, since 'name' & 'age' not defined in parent type
  // .. but exists nonetheless
  val person = Record(
    "name" -> "Emma",
    "age" -> 42
  ).asInstanceOf[Person]

  println(s"${person.name} is ${person.age} years old")

  person.selectDynamic("name").asInstanceOf[String] // looks like spark

  // Another structural type named Book
  type Book = Record {
    val title: String
    val author: String
    val year: Int
    val rating: Double
  }
  // this is how you create a Book instance
  val book = Record(
      "title" -> "The Catcher in the Rye",
      "author" -> "J. D. Salinger",
      "year" -> 1951,
      "rating" -> 4.5
  ).asInstanceOf[Book]
}
