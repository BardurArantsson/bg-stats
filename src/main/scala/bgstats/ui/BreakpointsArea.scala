package bgstats.ui

import bgstats.model.RenderedBreakpoint
import japgolly.scalajs.react.vdom.html_<^._
import japgolly.scalajs.react.ScalaComponent

object BreakpointsArea {

  case class Props(renderedBreakpoints: Seq[RenderedBreakpoint[VdomTag]])

  val Component = ScalaComponent.builder[Props]("BreakpointsArea")
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
          renderedBreakpoints.toVdomArray))
    })
    .build

}
