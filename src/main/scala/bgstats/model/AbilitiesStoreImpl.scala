package bgstats.model

import bgstats.model.Ability._
import monifu.concurrent.Scheduler
import monifu.reactive.OverflowStrategy
import monifu.reactive.channels.BehaviorChannel

class AbilitiesStoreImpl(implicit scheduler: Scheduler) extends AbilitiesStore with AbilitiesCommands {

  private[this] def clampValue(v: Int) =
    Math.min(25, Math.max(1, v))

  override val baseAbilities$ =
    BehaviorChannel(
      initial = Abilities.fromValues(
        STR -> 15,
        DEX -> 15,
        CON -> 15,
        INT -> 15,
        WIS -> 15,
        CHA -> 15),
      strategy = OverflowStrategy.Unbounded)

  override def updateAbilities(abilities: Abilities): Unit =
    baseAbilities$ := abilities.copy(
      values = abilities.values.mapValues(clampValue))

}
