package type_system

object UnionTypes {
  
  case class Username(name: String)
  case class Password(hash: String)


  def help(id: Username | Password) =
    val user = id match
      case Username(name) => ???
      case Password(hash) => ???
}
