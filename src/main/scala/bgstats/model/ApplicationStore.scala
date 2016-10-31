package bgstats.model

import bgstats.model.ApplicationStore.State
import monix.reactive.Observable

trait ApplicationStore[M] extends AllCommands {

  val state$: Observable[State[M]]

}

object ApplicationStore {

  case class State[M](
    baseSummaryAbilities: SummaryAbilities,
    inputChoices: Choices,
    renderedBreakpoints: Seq[RenderedBreakpoint[M]],
    deltaColumns: Vector[(AbilityColumn, Effects)],
    deltaTotal: Effects,
    totalsColumn: SummaryAbilities)

}
