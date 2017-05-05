package bgstats.ui

import bgstats.model.AbilityColumn
import bgstats.model.AbilityColumn.DreamColumn
import bgstats.model.AbilityColumn.HellColumn
import bgstats.model.AbilityColumn.MachineColumn
import bgstats.model.AbilityColumn.TomesColumn
import bgstats.model.{Effects, SummaryAbilities}
import japgolly.scalajs.react.CtorType
import japgolly.scalajs.react.vdom.html_<^._
import japgolly.scalajs.react.component.ScalaFn
import japgolly.scalajs.react.component.ScalaFn.Component

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

  private[this] def numberToString(i: Int): VdomTag =
    if (i == 0) {
      <.span("-")
    } else if (i < 0) {
      <.span(^.className := "negative", i)
    } else {
      <.span(^.className := "positive", i)
    }

  private[this] def row(key: String, label: String, columns: Vector[VdomTag]): VdomNode = {
    <.tr(
      ^.key := label,
      <.td(<.p(^.textAlign := "center", label)),
      columns.zipWithIndex.map { case (c, i) =>
        <.td(
          ^.key := i,
          <.p(^.className := "text-center", c)
        )
      }.toVdomArray
    )
  }

  val Component: Component[Props, CtorType.Props] = ScalaFn[Props](
    props => {
      // Stick all the columns together
      val columns: Vector[(String, SummaryAbilities)] =
        Vector("Base" -> props.baseSummaryAbilities) ++
          props.deltaColumns.map(dc => (columnDisplayName(dc._1), dc._2.deltaSummaryAbilities)) :+ ("Total" -> props.totalsColumn)
      // Render column headings
      val headingRow = {
        val headingColumns = columns.zipWithIndex.map { case ((k, _), i) =>
          <.th(
            ^.key := i,
            <.p(^.textAlign := "center", k))
        }
        // Prepend an empty heading and wrap up in row
        <.tr((Vector(<.th(^.key := "empty")) ++ headingColumns).toVdomArray)
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
          row(
            key = ability.displayName,
            label = ability.displayName,
            columns = renderedColumns)
        }
        // Magic resistance
        val magicResistanceRow = row(
          key = "MR%",
          label = "MR%",
          columns = columns.map(c => numberToString(c._2.magicResistance)))
        // Concat
        attributeRows :+ magicResistanceRow
      }
      // Extract the list of non-ability effects
      val effects = props.deltaColumns.map(_._2.effects).zipWithIndex flatMap { case (effect, i) =>
        effect.zipWithIndex.map { case (e, j) =>
          <.li(^.key := s"($i,$j)", e)
        }
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
              bodyRows.toVdomArray
            )
          )
        ),
        <.div(
          <.h2("Effects"),
          <.ul(
            ^.className := "square",
            effects.toVdomArray)))
    }
  )

}
