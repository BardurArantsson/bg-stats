package bgstats.model

import bgstats.model.Ability.CHA
import bgstats.model.Ability.CON
import bgstats.model.Ability.DEX
import bgstats.model.Ability.INT
import bgstats.model.Ability.STR
import bgstats.model.Ability.WIS
import scalaz._, Scalaz._

case class Abilities(values: Map[Ability, Int]) {

  /**
   * Total of all the abilities.
   */
  lazy val total: Int = values.values.sum

  /**
    * List of all the values sorted in "display order".
    */
  lazy val sortedValues: Vector[(Ability, Int)] =
    values.toVector.sortBy {
      case (k, v) => k.displayIndex
    }

}

object Abilities {

  val default: Abilities =
    Abilities.fromValues(
      STR -> 15,
      DEX -> 15,
      CON -> 15,
      INT -> 15,
      WIS -> 15,
      CHA -> 15)

  def fromValues(values: (Ability,Int)*): Abilities =
    Abilities(Map(values :_*))

  implicit object AbilitiesMonoid extends Monoid[Abilities] {
    override def zero: Abilities =
      Abilities(values = Map.empty)
    override def append(f1: Abilities, f2: => Abilities): Abilities =
      Abilities(values = f1.values |+| f2.values)
  }

}
