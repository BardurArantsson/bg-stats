package bgstats.ui

import bgstats.model.Ability
import bgstats.model.Ability._
import japgolly.scalajs.react.Callback
import japgolly.scalajs.react.vdom.html_<^._
import japgolly.scalajs.react.{ReactEventFromInput, ScalaComponent}

object DreamSacrifice {

  case class Props(name: String, dreamSacrifice: Option[Ability], onChange: Option[Ability] => Callback)

  val Component = ScalaComponent.builder[Props]("DreamSacrifice")
    .stateless
    .noBackend
    .render($ => {
      val props = $.props
      // Selection event handler
      def onSelected(event: ReactEventFromInput): Callback =
        $.props.onChange(Ability.fromString(event.target.value))
      // Render a single selector
      def selector(label: String, value: Option[Ability]): VdomArray = {
        val id = s"sacrifice-$label"
        Seq(
          <.input(
            ^.id := id,
            ^.`type` := "radio",
            ^.name := props.name,
            ^.value := value.map(Ability.toString).getOrElse(""),
            ^.checked := (props.dreamSacrifice == value),
            ^.onChange ==> onSelected
          ),
          <.label(
            ^.htmlFor := id,
            label
          )
        ).toVdomArray
      }
      // Render the field set
      <.fieldset(
        selector(label = "Cheat", value = None),
        selector(label = "DEX", value = Some(DEX)),
        selector(label = "CON", value = Some(CON)),
        selector(label = "INT", value = Some(INT)),
        selector(label = "WIS", value = Some(WIS))
      )
    })
    .build

}
