package type_system

object DesugarEnum {
  // COnceptually, enums can be thought of as defining a sealed class together with its companion object
  sealed abstract class Color(val rgb: Int) extends scala.reflect.Enum
  object Color:
    case object Red extends Color(0xFF0000) { def ordinal = 0 }
    case object Green extends Color(0x00FF00) { def ordinal = 1 }
    case object Blue extends Color(0x0000FF) { def ordinal = 2 }
    case class Mix(mix: Int) extends Color(mix) { def ordinal = 3 }

    def fromOrdinal(ordinal: Int): Color = ordinal match
      case 0 => Red
      case 1 => Green
      case 2 => Blue
      case _ => throw new NoSuchElementException(ordinal.toString)
}
