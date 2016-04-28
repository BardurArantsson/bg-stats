package bgstats.ui

import bgstats.model.RenderedBreakpoint
import japgolly.scalajs.react.ReactComponentC.ReqProps
import japgolly.scalajs.react.{ReactComponentB, TopNode}
import japgolly.scalajs.react.vdom.prefix_<^._

object BreakpointsArea {

  case class Props(renderedBreakpoints: Seq[RenderedBreakpoint[ReactTag]])

  val Component: ReqProps[Props, _, _, TopNode] = ReactComponentB[Props]("BreakpointsArea")
    .stateless
    .noBackend
    .render_P(props => {
      // Render the list of breakpoints.
      val renderedBreakpoints = props.renderedBreakpoints.map { renderedBreakpoint =>
        val breakpoint = renderedBreakpoint.breakpoint
        <.li(
          ^.classSet(
            "text-muted" -> !renderedBreakpoint.achieved),
          <.b(
            breakpoint.ability.displayName,
            " ",
            breakpoint.minimum),
          " ",
          breakpoint.message)
      }
      // Render component
      <.div(
        <.h2("Breakpoints"),
        <.ul(
          renderedBreakpoints))
    })
    .build

}
