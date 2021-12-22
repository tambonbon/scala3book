object UsingClauses_GivenInstances {
  /**
   * - Using clauses: allow you to specify params that, at the caller site, can be omitted & should be autom provided by context
   * - Given instances: let you define terms that can be used by Scala compiler to fill in missing arguments
   */

  object UsingClauses:
    // assume we need context info like settings needed to be provided to different components of your system
    case class Config(port: Int, baseUrl: String)

    def renderWebsiteTedious(path: String, c: Config): String = "<html>" + renderWidgetTedious(List("cart"), c) + "</html>"
    def renderWidgetTedious(items: List[String], c: Config): String = ???
    // passing `c` everytime is very tedious

    def renderWebsite(path: String)(using c: Config): String = 
      "<html>" + renderWidget(List("cart")) + "</html>"
    def renderWidget(items: List[String])(using c: Config): String = ???
    // By `using`, we tell the compiler that at the callsite it should automatically find an argument with correct type
    // ---> term inference
    
    val config = Config(1,"/base")
    renderWebsite("/home")(using config) // how to call contextual arguments

    def renderWebsiteOmit(path: String)(using Config) = ??? // no need to provide param name !!
  end UsingClauses

  object GivenInstances:
    import UsingClauses.*

    // We have seen we can explicitly pass arguments as contextual params by marking argument of the call with `using`
    // If there is a "single canonical value" for a particular type,..
    // .. there is another preferred way to make it available to compiler: 'given'
    val config = Config(8080, "docs.scala-lang.org")
    given Config = config

    renderWebsite("/home") // how to call contextual argumants w/o using
}

