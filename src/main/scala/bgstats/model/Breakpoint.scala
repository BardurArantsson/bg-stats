package bgstats.model

/**
 * Breakpoint
 */
case class Breakpoint[M](
  ability: Ability,
  minimum: Int,
  message: M)
