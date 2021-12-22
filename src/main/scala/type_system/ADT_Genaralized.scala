package type_system

//object ADT_Genaralized {
//  // an example of GADT where the type param T specifies the contents stored in the box
//  enum Box[T](contents: T):
//    case IntBox(n: Int) extends Box[Int](n)
//    case BoolBox(b: Boolean) extends Box[Boolean](b)
//
//  import math.Numeric.Implicits.inFixNumericOps
//  def extract[T](b: Box[T]): T = b match
//    case IntBox(n) => n + 1
//    case BoolBox(b) => !b
//}

