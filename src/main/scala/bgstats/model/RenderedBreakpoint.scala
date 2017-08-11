package bgstats.model

case class RenderedBreakpoint[M](
  breakpoint: Breakpoint[M],
  abilities: Abilities) {

  lazy val achieved: Boolean =
    abilities.values.getOrElse(breakpoint.ability, 0) >= breakpoint.minimum

}
