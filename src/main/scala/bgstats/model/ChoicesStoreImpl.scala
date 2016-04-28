package bgstats.model

import monifu.concurrent.Scheduler
import monifu.reactive.OverflowStrategy
import monifu.reactive.channels.BehaviorChannel

class ChoicesStoreImpl(implicit scheduler: Scheduler) extends ChoicesStore with ChoicesCommands {

  override val inputChoices$: BehaviorChannel[Choices] =
    BehaviorChannel(
      initial = Choices(
        bg1Tomes = true,
        machine = true,
        dreamSacrifice = None,
        trials = Trials.default),
      strategy = OverflowStrategy.Unbounded)

  override def updateChoices(choices: Choices): Unit =
    inputChoices$ := choices

}
