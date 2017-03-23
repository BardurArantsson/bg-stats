package bgstats.ui

import bgstats.model.Abilities
import bgstats.model.AbilitiesCommands
import japgolly.scalajs.react.CtorType
import japgolly.scalajs.react.vdom.html_<^._
import japgolly.scalajs.react.component.ScalaFn

object AbilitiesArea {

  case class Props(abilities: Abilities)(val abilitiesCommands: AbilitiesCommands)

  val Component: ScalaFn.Component[Props, CtorType.Props] = ScalaFn[Props](
    props => {
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
        renderedInputs.toVdomArray,
        AbilityRow.Component(
          AbilityRow.Props(
            label = "Total"
          )
        )(
          <.div(
            <.b(abilities.total)
          )
        )
      )
    }
  )

}
