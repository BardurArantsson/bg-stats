package bgstats.model

import scalaz.Monoid
import scalaz.syntax.monoid._

case class SummaryAbilities(abilities: Abilities, magicResistance: Int)

object SummaryAbilities {

  implicit object AbilitiesMonoid extends Monoid[SummaryAbilities] {
    override def zero: SummaryAbilities =
      SummaryAbilities(abilities = Monoid[Abilities].zero, magicResistance = 0)

    override def append(f1: SummaryAbilities, f2: => SummaryAbilities): SummaryAbilities =
      SummaryAbilities(abilities = f1.abilities |+| f2.abilities, magicResistance = f1.magicResistance + f2.magicResistance)
  }

}
