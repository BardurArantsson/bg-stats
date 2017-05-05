package bgstats.model

import bgstats.model.Ability._
import bgstats.model.Effects.EffectsMonoid
import bgstats.model.Orientation._
import scalaz.syntax.monoid._
import scalaz.Monoid

/**
 * In-game choices with permanent consequences.
 */
case class Choices(
  bg1Tomes: Boolean,
  machine: Boolean,
  dreamSacrifice: Option[Ability],
  trials: Trials) {

  /**
   * Calculate the effects of the dream choice.
   */
  lazy val dreamEffects: Effects = {
    dreamSacrifice match {
      case None =>
        Monoid[Effects].zero
      case Some(ability) =>
        Effects.abilities(ability -> -1)
    }
  }

  /**
   * Calculate the effects of the "Hell" trials choices.
   */
  lazy val trialsEffects: Effects = {
    // Effects of the "Wrath" hell trial.
    val wrathEffects = trials.wrath match {
      case Evil =>
        Effects.abilities(STR -> 2)
      case Good =>
        Effects.abilities(WIS -> 1, CHA -> 1)
    }
    // Effects of the "Fear" hell trial.
    val fearEffects = trials.fear match {
      case Evil =>
        Effects.abilities(CON -> 2)
      case Good =>
        Effects.text("Fear: Immunity to +1 weapons or less")
    }
    // Effects of the "Greed" hell trial
    val greedEffects = trials.greed match {
      case Good =>
        Effects.text("Greed: +2 all saving throws")
      case Evil =>
        Effects.text("Greed: +15 HP",
                     "Greed: Blackrazor")
    }
    // Effects of the "Selfish" hell trial
    val selfishEffects = trials.selfish match {
      case Good =>
        Effects(
          deltaSummaryAbilities = SummaryAbilities(
            abilities = Abilities.fromValues(DEX -> -1),
            magicResistance = 10),
          effects = Vector.empty)
      case Evil =>
        Effects.text("Selfish: +2 AC")
    }
    // Effects of the "Pride" hell trial
    val prideEffects = trials.pride match {
      case Good =>
        Effects.text("Pride: +20% resistance to Fire, Cold and Electricity")
      case Evil =>
        Effects.text("Pride: Gain 200,000 experience")
    }
    // Combine all the effects
    wrathEffects |+| fearEffects |+| greedEffects |+| selfishEffects |+| prideEffects
  }

  /**
   * Calculate effects of the Machine of Lum the Mad.
   */
  lazy val machineEffects: Effects = {
    if (machine) {
      // We'll just assume you press all the beneficial levers.
      Effects(deltaSummaryAbilities = SummaryAbilities(
        abilities = Abilities.fromValues(
          STR -> 1,
          DEX -> 1,
          CON -> 1,
          INT -> 1,
          WIS -> 1,
          CHA -> 1),
        magicResistance = 5),
        effects = Vector(
          "Storm Star +3"))
    } else {
      Monoid[Effects].zero
    }
  }

  /**
   * Calculate effects of the BG1 tomes.
   */
  lazy val bg1TomeEffects: Effects = {
    if (bg1Tomes) {
      // We'll assume you take all the tomes.
      Effects.abilities(
        STR -> 1,
        DEX -> 1,
        CON -> 1,
        INT -> 1,
        WIS -> 3,
        CHA -> 1)
    } else {
      Monoid[Effects].zero
    }
  }

}
