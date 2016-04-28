package bgstats.ui

import bgstats.model.Orientation
import bgstats.model.Orientation._
import japgolly.scalajs.react.Callback
import japgolly.scalajs.react.ReactComponentC.ReqProps
import japgolly.scalajs.react.{ReactComponentB, TopNode}
import japgolly.scalajs.react.vdom.prefix_<^._

object TrialChoice {

  case class Props(name: String, label: String, orientation: Orientation, onChange: Orientation => Callback)

  val Component: ReqProps[Props, _, _, TopNode] = ReactComponentB[Props]("TrialChoice")
    .stateless
    .noBackend
    .render($ => {
      val props = $.props
      val idPrefix = s"choice-${props.name}"
      val goodId = s"$idPrefix-good"
      val evilId = s"$idPrefix-evil"
      <.fieldset(
        ^.className :="medium-6 columns",
        <.legend(
          <.b(props.label)
        ),
        <.input(
          ^.id := goodId,
          ^.`type` := "radio",
          ^.name := props.name,
          ^.checked := (props.orientation == Good),
          ^.onChange --> props.onChange(Good)),
        <.label(
          ^.htmlFor := goodId,
          "Good"
        ),
        <.input(
          ^.id := evilId,
          ^.`type` := "radio",
          ^.name := props.name,
          ^.checked := (props.orientation == Evil),
          ^.onChange --> props.onChange(Evil)),
        <.label(
          ^.htmlFor := evilId,
          "Evil"
        )
      )
    })
    .build

}
