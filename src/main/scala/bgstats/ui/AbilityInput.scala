package bgstats.ui

import bgstats.model.Ability
import japgolly.scalajs.react.Callback
import japgolly.scalajs.react.{ReactEventFromInput, ScalaComponent}
import japgolly.scalajs.react.vdom.html_<^._

import scala.util.Try

object AbilityInput {

  case class Props(
    ability: Ability,
    value: Int,
    onChange: Int => Unit)

  val Component = ScalaComponent.builder[Props]("AbilityInput")
    .stateless
    .noBackend
    .render($ => {
      val props = $.props
      // Change callback
      def changed(event: ReactEventFromInput): Callback = Callback {
        Try(event.target.value.toInt).toOption match {
          case Some(newValue) => props.onChange(newValue)
          case None           => () // ignore
        }
      }
      // Render
      AbilityRow.Component(
        AbilityRow.Props(
          label = props.ability.displayName
        )
      )(
        <.div(
          <.input(
            ^.`type` := "number",
            ^.step := "1",
            ^.value := props.value.toString,
            ^.onChange ==> changed
          )
        )
      )
    })
    .build

}
