package bgstats.ui

import japgolly.scalajs.react.ReactComponentC.ReqProps
import japgolly.scalajs.react.{TopNode, ReactComponentB}
import japgolly.scalajs.react.vdom.prefix_<^._

object AbilityRow {

  case class Props(label: String)

  val Component: ReqProps[Props, _, _, TopNode] = ReactComponentB[Props]("AbilityRow")
    .stateless
    .noBackend
    .renderPC((_, props, children) => {
      <.div(
        ^.className := "row",
        <.div(
          ^.className := "medium-4 columns",
          <.label(
            ^.className := "text-right",
            ^.className := "middle",
            props.label
          )
        ),
        <.div(
          ^.className := "medium-8 columns",
          children
        )
      )
    })
    .build

}
