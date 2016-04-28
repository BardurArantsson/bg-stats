package bgstats.ui

import bgstats.model.AbilityColumn
import bgstats.model.AbilityColumn.DreamColumn
import bgstats.model.AbilityColumn.HellColumn
import bgstats.model.AbilityColumn.MachineColumn
import bgstats.model.AbilityColumn.TomesColumn
import bgstats.model.{Effects, SummaryAbilities}
import japgolly.scalajs.react.ReactComponentC.ReqProps
import japgolly.scalajs.react.vdom.prefix_<^._
import japgolly.scalajs.react.{ReactComponentB, ReactNode, TopNode}

object TotalsArea {

  case class Props(
    baseSummaryAbilities: SummaryAbilities,
    deltaColumns: Vector[(AbilityColumn, Effects)],
    totalsColumn: SummaryAbilities)

  private[this] def columnDisplayName(abilityColumn: AbilityColumn): String =
    abilityColumn match {
      case DreamColumn => "Dream"
      case HellColumn => "Hell Trials"
      case MachineColumn => "MoLtM"
      case TomesColumn => "BG1 Tomes"
    }

  private[this] def numberToString(i: Int): ReactTag =
    if (i == 0) {
      <.span("-")
    } else if (i < 0) {
      <.span(^.className := "negative", i)
    } else {
      <.span(^.className := "positive", i)
    }

  private[this] def row(label: String, columns: Vector[ReactTag]): ReactNode = {
    <.tr(
      <.td(<.p(^.textAlign := "center", label)),
      columns.map(c => <.td(<.p(^.className := "text-center", c))))
  }

  val Component: ReqProps[Props, _, _, TopNode] = ReactComponentB[Props]("TotalsArea")
    .stateless
    .noBackend
    .render_P(props => {
      // Stick all the columns together
      val columns: Vector[(String, SummaryAbilities)] =
        Vector("Base" -> props.baseSummaryAbilities) ++
          props.deltaColumns.map(dc => (columnDisplayName(dc._1), dc._2.deltaSummaryAbilities)) :+ ("Total" -> props.totalsColumn)
      // Render column headings
      val headingRow = {
        val headingColumns = columns.map { case (k, _) =>
          <.th(<.p(^.textAlign := "center", k))
        }
        // Prepend an empty heading and wrap up in row
        <.tr(Vector(<.th()) ++ headingColumns)
      }
      // Render rows for the table body
      val bodyRows = {
        // Extract all used Ability values and sort by displayIndex
        val sortedAbilities = columns.flatMap(_._2.abilities.values.keys).distinct.sortBy(_.displayIndex)
        // Generate rows for all the abilities
        val attributeRows = sortedAbilities.map { ability =>
          // Row data
          val renderedColumns = columns.map { case (_, column) =>
            column.abilities.values.get(ability).map(numberToString).getOrElse(<.span("-"))
          }
          // Row done
          row(ability.displayName, renderedColumns)
        }
        // Magic resistance
        val magicResistanceRow = row("MR%",
          columns.map(c => numberToString(c._2.magicResistance)))
        // Concat
        attributeRows :+ magicResistanceRow
      }
      // Extract the list of non-ability effects
      val effects = props.deltaColumns.map(_._2.effects) flatMap { effect =>
        effect.map(e => <.li(e))
      }
      // Render component
      <.div(
        <.div(
          <.h2("Abilities"),
          <.table(
            <.thead(
              headingRow
            ),
            <.tbody(
              bodyRows
            )
          )
        ),
        <.div(
          <.h2("Effects"),
          <.ul(
            ^.className := "square",
            effects)))
    })
    .build

}
