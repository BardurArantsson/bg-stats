package bgstats.model

import bgstats.monix_ext.Var
import monix.execution.Scheduler
import monix.reactive.Observable

class ChoicesStoreImpl(implicit scheduler: Scheduler) extends ChoicesStore with ChoicesCommands {

  private[this] val inputChoicesV: Var[Choices] = Var(
    Choices.default)

  override val inputChoices$: Observable[Choices] =
    inputChoicesV.value$

  override def updateChoices(choices: Choices): Unit =
    inputChoicesV.set(choices)

}
