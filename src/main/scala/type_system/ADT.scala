package type_system

enum Option[+T] {
  case Some(x: T) // extends Option[T]
  case None // extends Option[Nothing]

  def isDefined: Boolean = this match 
    case None => false
    case Some(_) => true
}

object Option:
  def apply[T >: Null](x: T): Option[T] =
    if (x == null) None else Some(x)

// Enumerations and ADTs share same syntactic construct
// The code below gives an implementation of Color, 
// either with 3 enum values, 
// or with a parameterized case taking an RGB value
enum Color(val rgb: Int):
  case Red extends Color(0xFF0000)
  case GREEN extends Color(0x00FF00)
  case Blue extends Color(0x0000FF)
  case Mix(mix: Int) extends Color(mix)
