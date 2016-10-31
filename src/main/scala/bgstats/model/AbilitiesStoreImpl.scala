package bgstats.model

import bgstats.model.Ability._
import bgstats.monix_ext.Var
import monix.execution.Scheduler

class AbilitiesStoreImpl(implicit scheduler: Scheduler) extends AbilitiesStore with AbilitiesCommands {

  private[this] def clampValue(v: Int) =
    Math.min(25, Math.max(1, v))

  private[this] val baseAbilitiesV = Var[Abilities](
    Abilities.fromValues(
        STR -> 15,
        DEX -> 15,
        CON -> 15,
        INT -> 15,
        WIS -> 15,
        CHA -> 15))

  override val baseAbilities$ =
    baseAbilitiesV.value$

  override def updateAbilities(abilities: Abilities): Unit =
    baseAbilitiesV.set(abilities.copy(
      values = abilities.values.mapValues(clampValue)))

}
