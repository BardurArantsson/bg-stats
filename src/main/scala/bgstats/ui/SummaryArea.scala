package bgstats.ui

import bgstats.model.{Choices, Abilities}
import japgolly.scalajs.react.ScalaComponent
import japgolly.scalajs.react.vdom.html_<^._

object SummaryArea {

  case class Props(baseAbilities: Abilities, choices: Choices)

  private[this] val indent = "     "

  private[this] def renderBoolean(b: Boolean): String = {
    if (b) { "Y" } else { "N" }
  }

  private[this] def renderAbilities(abilities: Abilities): TagMod = {
    abilities.sortedValues.map { case (ability, value) =>
      s"$indent${ability.displayName}: $value\n"
    }.toTagMod
  }

  private[this] def renderChoices(choices: Choices): TagMod = {
    import choices._
    Vector(
      s"${indent}Collect BG1 Tomes: ${renderBoolean(bg1Tomes)}\n",
      s"${indent}Use MoLtM: ${renderBoolean(machine)}\n",
      s"${indent}Dream Sacrifice: ${dreamSacrifice.getOrElse("-")}\n"
    ).toTagMod
  }

  private[this] def renderTrials(choices: Choices): TagMod = {
    import choices.trials._
    Vector(
      s"${indent}Wrath: $wrath\n",
      s"${indent}Fear: $fear\n",
      s"${indent}Greed: $greed\n",
      s"${indent}Pride: $pride\n",
      s"${indent}Selfish: $selfish\n"
    ).toTagMod
  }

  val Component = ScalaComponent.builder[Props]("SummaryArea")
    .stateless
    .noBackend
    .render($ => {
      val p = $.props
      <.div(
        <.h2("Summary"),
        <.pre(
          "Abilities:\n\n",
          renderAbilities(p.baseAbilities),
          "\n",
          "In-game choices:\n\n",
          renderChoices(p.choices),
          "\n",
          "Hell Trials:\n\n",
          renderTrials(p.choices)
        )
      )
    })
    .build

}
