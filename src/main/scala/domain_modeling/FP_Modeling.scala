package domain_modeling

/**
 * An FP design is implemented by
 * - Describe your set of values (your data)
 * - Describe operations that work on those values (your functions)
 */
object FP_Modeling {

  enum CrustSize:
    case Small, Medium, Large

  enum CrustType:
    case Thin, Thick, Regular

  enum Topping:
    case Cheese, Pepperoni, BlackOlives, GreenOlives, Onions

  case class Pizza(
      crustSize: CrustSize,
      crustType: CrustType,
      toppings: Seq[Topping]
  )

  /** How to organizse functionality
    */

  // Method 1: Companion object
  object Pizza:
    def price(pizza: Pizza): Double = ???

  object Topping:
    def price(toppings: Topping): Double = toppings match
      case Cheese | Onions => 0.5
      case _               => 0.75

  /** Drawbacks
    *   - It tightly couples functionality to your data model; it needs to be
    *     defined in the same file as your case class
    *   - It might be unclear where to define functions
    */

  //  Method 2: Module
  trait PizzaServiceInterface: // all non-abstract classes that extend this trait MUST provide an impl of these services

    def price(p: Pizza): Double

    def addTopping(p: Pizza, t: Topping): Pizza
    def removeAllToppings(p: Pizza): Pizza

    def updateCrustSize(p: Pizza, cs: CrustSize): Pizza
    def updateCrustType(p: Pizza, ct: CrustType): Pizza

  object PizzaService extends PizzaServiceInterface:
    def price(p: Pizza): Double = ???
    def addTopping(p: Pizza, t: Topping): Pizza =
      p.copy(toppings = p.toppings :+ t)

    def removeAllToppings(p: Pizza): Pizza =
      p.copy(toppings = Seq.empty)

    def updateCrustSize(p: Pizza, cs: CrustSize): Pizza =
      p.copy(crustSize = cs)

    def updateCrustType(p: Pizza, ct: CrustType): Pizza =
      p.copy(crustType = ct)
  end PizzaService

  // Method 3: Functional Objects
  case class PizzaFunctional(
      crustSize: CrustSize,
      crustType: CrustType,
      toppings: Seq[Topping]
  ):
    // the operations on the data model
    def price: Double = ??? // implementation from above

    def addTopping(t: Topping): PizzaFunctional =
      this.copy(toppings = this.toppings :+ t)

    def removeAllToppings: PizzaFunctional =
      this.copy(toppings = Seq.empty)

    def updateCrustSize(cs: CrustSize): PizzaFunctional =
      this.copy(crustSize = cs)

    def updateCrustType(ct: CrustType): PizzaFunctional =
      this.copy(crustType = ct)
  // Method 4: Extension method
  // Extension method let us create an API that is like the one of functional object..
  // .. without having to define functions as methods on the type itself

  case class PizzaExtension(
      crustSize: CrustSize,
      crustType: CrustType,
      toppings: Seq[Topping]
  )

  // extension lets you add new functionality to closed classes, such as when PizzaExtension is from 3rd-party library
  extension (p: PizzaExtension)
    def price: Double = ???
    def addToppingExtension(t: Topping): PizzaExtension =
      p.copy(toppings = p.toppings :+ t)

    def removeAllToppingsExtension: PizzaExtension =
      p.copy(toppings = Seq.empty)

    def updateCrustSizeExtension(cs: CrustSize): PizzaExtension =
      p.copy(crustSize = cs)

    def updateCrustTypeExtension(ct: CrustType): PizzaExtension =
      p.copy(crustType = ct)
}

import FP_Modeling.*
import CrustSize.*
import CrustType.*
import Topping.*
import PizzaService.*
// import PizzaExtension.*
@main def main = 
  // Method 1
  val pizza1 = Pizza(Small, Thin, Seq(Cheese, Onions))
  Pizza.price(pizza1)

  // Method 2
  val p = Pizza(Small, Thin, Seq(Cheese))

// how you want to use the methods in PizzaServiceInterface
  val p1 = addTopping(p, Pepperoni)
  val p2 = addTopping(p1, Onions)
  val p3 = updateCrustType(p2, Thick)
  val p4 = updateCrustSize(p3, Large)

  // Method 3
  PizzaFunctional(Small, Thin, Seq(Cheese))
  .addTopping(Pepperoni)
  .updateCrustType(Thick)
  .price

  // Method 4
  PizzaExtension(Small, Thin, Seq(Cheese))
  .addToppingExtension(Pepperoni)
  .updateCrustTypeExtension(Thick)
  .price