package bgstats.ui

import bgstats.model.Orientation
import bgstats.model.Orientation._
import japgolly.scalajs.react.Callback
import japgolly.scalajs.react.CtorType
import japgolly.scalajs.react.component.ScalaFn
import japgolly.scalajs.react.component.ScalaFn.Component
import japgolly.scalajs.react.vdom.html_<^._

object TrialChoice {

  case class Props(name: String, label: String, orientation: Orientation, onChange: Orientation => Callback)

  val Component: Component[Props, CtorType.Props] = ScalaFn[Props](
    props => {
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
    }
  )

}
