package bgstats.model

import bgstats.monix_ext.Var
import monix.execution.Scheduler
import monix.reactive.Observable

class AbilitiesStoreImpl(implicit scheduler: Scheduler) extends AbilitiesStore with AbilitiesCommands {

  private[this] def clampValue(v: Int) =
    Math.min(25, Math.max(1, v))

  private[this] val baseAbilitiesV = Var[Abilities](
    Abilities.default)

  override val baseAbilities$: Observable[Abilities] =
    baseAbilitiesV.value$

  override def updateAbilities(abilities: Abilities): Unit =
    baseAbilitiesV.set(abilities.copy(
      values = abilities.values.mapValues(clampValue)))

}
