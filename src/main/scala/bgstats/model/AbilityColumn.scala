package bgstats.model

sealed trait AbilityColumn

object AbilityColumn {

  case object DreamColumn extends AbilityColumn
  case object HellColumn extends AbilityColumn
  case object MachineColumn extends AbilityColumn
  case object TomesColumn extends AbilityColumn

}
