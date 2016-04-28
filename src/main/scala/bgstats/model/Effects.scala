package bgstats.model

import bgstats.model.Abilities.AbilitiesMonoid
import scalaz.syntax.monoid._
import scalaz.Monoid
import scalaz.std.vector._

case class Effects(deltaSummaryAbilities: SummaryAbilities, effects: Vector[String])

object Effects {

  /**
   * Monoid instance
   */
  implicit object EffectsMonoid extends Monoid[Effects] {

    override def zero: Effects =
      Effects(deltaSummaryAbilities = Monoid[SummaryAbilities].zero, effects = Vector.empty)

    override def append(f1: Effects, f2: => Effects): Effects =
      Effects(deltaSummaryAbilities = f1.deltaSummaryAbilities |+| f2.deltaSummaryAbilities, effects = f1.effects |+| f2.effects)

  }

  /**
   * Constructor for effects that only change abilities.
   */
  def abilities(a: (Ability, Int)*): Effects = Effects(
    deltaSummaryAbilities = SummaryAbilities(abilities = Abilities(Map(a :_*)), magicResistance = 0), effects = Vector.empty)

  /**
   * Constructor for effects that only have descriptive text.
   */
  def text(t: String*): Effects = Effects(deltaSummaryAbilities = Monoid[SummaryAbilities].zero, effects = Vector(t :_*))

}

