package bgstats.ui

import bgstats.model.Abilities
import bgstats.model.AbilitiesCommands
import japgolly.scalajs.react.ReactComponentC.ReqProps
import japgolly.scalajs.react.{ReactComponentB, TopNode}
import japgolly.scalajs.react.vdom.prefix_<^._

object AbilitiesArea {

  case class Props(abilities: Abilities)(val abilitiesCommands: AbilitiesCommands)

  val Component: ReqProps[Props, _, _, TopNode] = ReactComponentB[Props]("AbilitiesArea")
    .stateless
    .noBackend
    .render_P(props => {
      import props._
      // Generate the inputs for each of the abilities
      val renderedInputs = abilities.sortedValues.map { case (k, value) =>
        AbilityInput.Component(
          AbilityInput.Props(
            ability = k,
            value = value,
            onChange = vNew =>
              abilitiesCommands.updateAbilities(Abilities(
                values = abilities.values.updated(k, vNew)))
          ))
      }
      // Render component
      <.fieldset(
        ^.className := "fieldset",
        <.legend("Abilities"),
        renderedInputs,
        AbilityRow.Component(
          AbilityRow.Props(
            label = "Total"
          ),
          <.div(
            <.b(abilities.total)
          )
        )
      )
    })
    .build

}
