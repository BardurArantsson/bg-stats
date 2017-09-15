package bgstats.ui

import bgstats.model.AllCommands
import bgstats.model.ApplicationState
import japgolly.scalajs.react.CtorType
import japgolly.scalajs.react.component.ScalaFn
import japgolly.scalajs.react.component.ScalaFn.Component
import japgolly.scalajs.react.vdom.html_<^._

object Application {

  case class Props(state: ApplicationState[VdomTag])(val allCommands: AllCommands)

  val Component: Component[Props, CtorType.Props] = ScalaFn[Props](
    props => {
      val state = props.state
      val commands = props.allCommands
      <.div(
        <.div(
          ^.className := "row",
          <.div(
            ^.key := "abilities-choices-and-breakpoints",
            ^.className := "medium-7 columns",
            <.div(
              ^.key := "abilities-and-choices",
              ^.className := "row",
              <.div(
                ^.key := "abilities-area",
                ^.className := "medium-4 columns",
                AbilitiesArea.Component(
                  AbilitiesArea.Props(
                    abilities = state.baseSummaryAbilities.abilities)(commands)
                )
              ),
              <.div(
                ^.key := "choices-area",
                ^.className := "medium-8 columns",
                ChoicesArea.Component(
                  ChoicesArea.Props(
                    state.inputChoices)(commands)
                )
              )
            ),
            <.div(
              ^.key := "breakpoints-area",
              ^.className := "row",
              <.div(
                ^.className := "medium-12 columns",
                BreakpointsArea.Component(
                  BreakpointsArea.Props(
                    renderedBreakpoints = state.renderedBreakpoints)
                )
              )
            )
          ),
          <.div(
            ^.key := "totals-and-summary",
            ^.className := "medium-5 columns",
            <.div(
              ^.key := "totals-area",
              ^.className := "row",
              <.div(
                ^.className := "medium-12 columns",
                TotalsArea.Component(
                TotalsArea.Props(
                  baseSummaryAbilities = state.baseSummaryAbilities,
                  deltaColumns = state.deltaColumns,
                  totalsColumn = state.totalsColumn)
                )
              )
            ),
            <.div(
              ^.key := "summary-area",
              ^.className := "row",
              <.div(
                ^.className := "medium-12 columns",
                SummaryArea.Component(
                  SummaryArea.Props(
                    choices = state.inputChoices,
                    baseAbilities = state.baseSummaryAbilities.abilities)
                )
              )
            )
          )
        )
      )
    }
  )

}
