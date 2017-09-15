package bgstats.model

import scalaz.syntax.monoid._

case class ApplicationState[M](
  baseAbilities: Abilities,
  inputChoices: Choices)(
  breakpoints: Seq[Breakpoint[M]]) {

  lazy val baseSummaryAbilities: SummaryAbilities = SummaryAbilities(
    abilities = baseAbilities,
    magicResistance = 0)

  lazy val totalsColumn: SummaryAbilities =
    baseSummaryAbilities |+|
      inputChoices.deltaTotal.deltaSummaryAbilities

  lazy val renderedBreakpoints: Seq[RenderedBreakpoint[M]] =
    breakpoints.map(RenderedBreakpoint(_, totalsColumn.abilities))

  lazy val deltaColumns: Vector[(AbilityColumn, Effects)] =
    inputChoices.deltaColumns

}
