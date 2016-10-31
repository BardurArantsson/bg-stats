package bgstats.model

import bgstats.monix_ext.Var
import monix.execution.Scheduler

class ChoicesStoreImpl(implicit scheduler: Scheduler) extends ChoicesStore with ChoicesCommands {

  private[this] val inputChoicesV: Var[Choices] = Var(
    Choices(
      bg1Tomes = true,
      machine = true,
      dreamSacrifice = None,
      trials = Trials.default))

  override val inputChoices$ = inputChoicesV.value$

  override def updateChoices(choices: Choices): Unit =
    inputChoicesV.set(choices)

}
