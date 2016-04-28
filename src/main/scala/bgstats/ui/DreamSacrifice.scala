package bgstats.ui

import bgstats.model.Ability
import bgstats.model.Ability._
import japgolly.scalajs.react.Callback
import japgolly.scalajs.react.ReactComponentC.ReqProps
import japgolly.scalajs.react.vdom.prefix_<^._
import japgolly.scalajs.react.{ReactComponentB, ReactEventI, TopNode}

object DreamSacrifice {

  case class Props(name: String, dreamSacrifice: Option[Ability], onChange: Option[Ability] => Callback)

  val Component: ReqProps[Props, _, _, TopNode] = ReactComponentB[Props]("DreamSacrifice")
    .stateless
    .noBackend
    .render($ => {
      val props = $.props
      // Selection event handler
      def onSelected(event: ReactEventI): Callback =
        $.props.onChange(Ability.fromString(event.target.value))
      // Render a single selector
      def selector(label: String, value: Option[Ability]): Seq[ReactTag] = {
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
        )
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
