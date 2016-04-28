package bgstats

import bgstats.model.ApplicationStore
import bgstats.model.ApplicationStoreImpl
import bgstats.model.Ability._
import bgstats.model.AbilitiesStore
import bgstats.model.AbilitiesStoreImpl
import bgstats.model.Breakpoint
import bgstats.model.ChoicesStore
import bgstats.model.ChoicesStoreImpl
import bgstats.ui.Application
import japgolly.scalajs.react.ReactDOM
import japgolly.scalajs.react.vdom.prefix_<^._
import monifu.concurrent.Implicits.globalScheduler
import org.scalajs.dom.document

import scala.scalajs.js.JSApp

object Boot extends JSApp {

  /**
   * Breakpoints for certain desirable items/effects.
   */
  val breakpoints = Vector[Breakpoint[ReactTag]](
    Breakpoint(ability = INT, minimum = 16, message = <.span("for the Golem manual")),
    Breakpoint(ability = INT, minimum = 12, message = <.span("to survive 3 hits from Illithid ", <.b("with Potion of Intelligence"))),
    Breakpoint(ability = INT, minimum = 16, message = <.span("to survive 3 hits from Illithid")),
    Breakpoint(ability = INT, minimum = 17, message = <.span("to survive 4 hit from Illithid ", <.b("with Potion of Intelligence"))),
    Breakpoint(ability = WIS, minimum = 13, message = <.span("to convince the Spectator")),
    Breakpoint(ability = WIS, minimum = 18, message = <.span("for Wish")),
    Breakpoint(ability = CON, minimum = 16, message = <.span("for max CON bonus (non-fighter)")),
    Breakpoint(ability = CON, minimum = 18, message = <.span("for max CON conus (non-fighter) with ", <.b("Claw of Kazgaroth"))),
    Breakpoint(ability = CON, minimum = 18, message = <.span("for shorty saving throw bonus (+1)")),
    Breakpoint(ability = CON, minimum = 20, message = <.span("for innate regeneration")),
    Breakpoint(ability = CON, minimum = 21, message = <.span("for shorty saving throw bonus (+2) ", <.b("with aTweaks"))),
    Breakpoint(ability = CHA, minimum = 20, message = <.span("for max shopping bonus")),
    Breakpoint(ability = CHA, minimum = 18, message = <.span("for only needing +2 CHA cape for max shopping bonus")),
    Breakpoint(ability = CHA, minimum = 14, message = <.span("for only needing Friendship")),
    Breakpoint(ability = CHA, minimum = 12, message = <.span("for only needing Friendship + 2 CHA cape for max shopping bonus")))

  /**
   * Entry point to be called from JS.
   */
  override def main(): Unit = {
    // Set up all the stores
    val choicesStore: ChoicesStore = new ChoicesStoreImpl()
    val abilitiesStore: AbilitiesStore = new AbilitiesStoreImpl()
    val applicationStore: ApplicationStore[ReactTag] = new ApplicationStoreImpl(
      breakpoints = breakpoints,
      abilitiesStore = abilitiesStore,
      choicesStore = choicesStore)
    // Start up; everything is driven by the ApplicationStore's state observable.
    applicationStore.state$.foreach { view =>
      val application =
        Application.Component(
          Application.Props(view)(applicationStore))
      ReactDOM.render(application, document.getElementById("application"))
    }
  }

}
