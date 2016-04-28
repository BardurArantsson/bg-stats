package bgstats.model

sealed abstract class Ability(val displayName: String, val displayIndex: Int)

object Ability {

  case object STR extends Ability("STR", 0)
  case object DEX extends Ability("DEX", 1)
  case object CON extends Ability("CON", 2)
  case object INT extends Ability("INT", 3)
  case object WIS extends Ability("WIS", 4)
  case object CHA extends Ability("CHA", 5)

  def toString(a: Ability): String =
    a match {
      case STR => "STR"
      case CON => "CON"
      case DEX => "DEX"
      case WIS => "WIS"
      case INT => "INT"
      case CHA => "CHA"
    }

  def fromString(s: String): Option[Ability] =
    s match {
      case "STR" => Some(STR)
      case "CON" => Some(CON)
      case "DEX" => Some(DEX)
      case "WIS" => Some(WIS)
      case "INT" => Some(INT)
      case "CHA" => Some(CHA)
      case _ => None
    }

}
