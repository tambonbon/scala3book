object OOP_Modeling {
  case class Subject()
  case class Observer()

  trait SubjectObserver:

    type S <: Subject
    type O <: Observer

    trait Subject { self: S => // requires subtypes of Subject to also be subtypes of S ..
      private var observers: List[O] = List()
      def subscribe(obs: O): Unit =
        observers = obs :: observers
      def publish() =
        for obs <- observers do obs.notify(this) // .. bc `obs.notify(this)` requires a value of type S ..
                                                 // .. If `S` was a concrete type, the trait'd have been `trait Subject extends S`
  }

  trait Observer {
    def notify(sub: S): Unit
  }

  object SensorReader extends SubjectObserver:
    type S = Sensor
    type O = Display

  class Sensor(val label: String) extends Subject:
    private var currentValue = 0.0
    def value = currentValue
    def changeValue(v: Double) =
      currentValue = v
      publish()

  class Display extends Observer:
    def notify(sub: Sensor) =
      println(s"${sub.label} has value ${sub.value}")
}
