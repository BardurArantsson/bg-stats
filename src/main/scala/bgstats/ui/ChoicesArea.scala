package bgstats.ui

import bgstats.model.ChoicesCommands
import bgstats.model.Trials
import bgstats.model.Choices
import japgolly.scalajs.react.Callback
import japgolly.scalajs.react.ReactComponentC.ReqProps
import japgolly.scalajs.react.{ReactEventI, ReactComponentB, TopNode}
import japgolly.scalajs.react.vdom.prefix_<^._

object ChoicesArea {

  case class Props(choices: Choices)(val choicesCommands: ChoicesCommands)

  val Component: ReqProps[Props, _, _, TopNode] = ReactComponentB[Props]("ChoicesArea")
    .stateless
    .noBackend
    .render($ => {
      val props = $.props
      // Generic functions for updating choices and trials
      def updateChoices(f: Choices => Choices): Callback = Callback {
        props.choicesCommands.updateChoices(f(props.choices))
      }
      def updateTrial(f: Trials => Trials): Callback =
        updateChoices(choices => choices.copy(trials = f(choices.trials)))
      // Render the BG1 tome choice
      val bg1TomeChoice =
        <.div(
          <.label(
            <.input(
              ^.`type` := "checkbox",
              ^.checked := props.choices.bg1Tomes,
              ^.onChange ==> ((e: ReactEventI) => updateChoices(_.copy(bg1Tomes = e.target.checked)))),
            "Collect all BG1 Tomes"))
      // Render the MoLtM choice
      val moltmChoice =
        <.div(
          <.label(
            <.input(
              ^.`type` := "checkbox",
              ^.checked := props.choices.machine,
              ^.onChange ==> ((e: ReactEventI) => updateChoices(_.copy(machine = e.target.checked)))),
            "Use Machine of Lum the Mad"))
      // Render the Wrath trial choice
      val wrathChoice =
        TrialChoice.Component(
          TrialChoice.Props(
            name = "wrath",
            label = "Wrath",
            orientation = props.choices.trials.wrath,
            onChange = o => updateTrial(_.copy(wrath = o))))
      val fearChoice =
        TrialChoice.Component(
          TrialChoice.Props(
            name = "fear",
            label = "Fear",
            orientation = props.choices.trials.fear,
            onChange = o => updateTrial(_.copy(fear = o))))
      val greedChoice =
        TrialChoice.Component(
          TrialChoice.Props(
            name = "greed",
            label = "Greed",
            orientation = props.choices.trials.greed,
            onChange = o => updateTrial(_.copy(greed = o))))
      val prideChoice =
        TrialChoice.Component(
          TrialChoice.Props(
            name = "pride",
            label = "Pride",
            orientation = props.choices.trials.pride,
            onChange = o => updateTrial(_.copy(pride = o))))
      val selfishChoice =
        TrialChoice.Component(
          TrialChoice.Props(
            name = "selfish",
            label = "Selfish",
            orientation = props.choices.trials.selfish,
            onChange = o => updateTrial(_.copy(selfish = o))))
      // Render the combined form
      <.form(
        <.fieldset(
          ^.className := "fieldset",
          <.legend("In-game Choices"),
          bg1TomeChoice,
          moltmChoice
        ),
        <.fieldset(
          ^.className := "fieldset",
          <.legend(
            "Dream Sacrifice"
          ),
          DreamSacrifice.Component(
            DreamSacrifice.Props(
              name = "sacrifice",
              dreamSacrifice = props.choices.dreamSacrifice,
              onChange = s => updateChoices(_.copy(dreamSacrifice = s))
            )
          )
        ),
        <.fieldset(
          ^.className := "fieldset",
          <.legend("Hell Trials"),
          <.div(
            ^.className := "row",
            wrathChoice,
            fearChoice,
            greedChoice,
            prideChoice,
            selfishChoice
          )
        )
      )
    })
    .build

}
