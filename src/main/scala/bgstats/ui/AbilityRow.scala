package bgstats.ui

import japgolly.scalajs.react.CtorType
import japgolly.scalajs.react.component.ScalaFn
import japgolly.scalajs.react.component.ScalaFn.Component
import japgolly.scalajs.react.vdom.html_<^._

object AbilityRow {

  case class Props(label: String)

  val Component: Component[Props, CtorType.PropsAndChildren] = ScalaFn.withChildren[Props](
    (props, children) => {
      <.div(
        ^.className := "row",
        <.div(
          ^.key := "label",
          ^.className := "medium-4 columns",
          <.label(
            ^.className := "text-right",
            ^.className := "middle",
            props.label
          )
        ),
        <.div(
          ^.key := "control",
          ^.className := "medium-8 columns",
          children
        )
      )
    }
  )

}
