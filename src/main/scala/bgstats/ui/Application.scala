package bgstats.ui

import bgstats.model.AllCommands
import bgstats.model.ApplicationStore
import japgolly.scalajs.react.ReactComponentC.ReqProps
import japgolly.scalajs.react.vdom.prefix_<^._
import japgolly.scalajs.react.ReactComponentB
import japgolly.scalajs.react.TopNode

object Application {

  case class Props(state: ApplicationStore.State[ReactTag])(val allCommands: AllCommands)

  val Component: ReqProps[Props, _, _, TopNode] = ReactComponentB[Props]("Application")
    .stateless
    .noBackend
    .render($ => {
      val state = $.props.state
      val commands = $.props.allCommands
      <.div(
        <.div(
          ^.className := "row",
          <.div(
            ^.className := "medium-7 columns",
            <.div(
              ^.className := "row",
              <.div(
                ^.className := "medium-4 columns",
                AbilitiesArea.Component(
                  AbilitiesArea.Props(
                    abilities = state.baseSummaryAbilities.abilities)(commands)
                )
              ),
              <.div(
                ^.className := "medium-8 columns",
                ChoicesArea.Component(
                  ChoicesArea.Props(
                    state.inputChoices)(commands)
                )
              )
            ),
            <.div(
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
            ^.className := "medium-5 columns",
            <.div(
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
    })
    .build

}
