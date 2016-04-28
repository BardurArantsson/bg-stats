package bgstats.model

sealed trait Orientation

object Orientation {

  case object Good extends Orientation
  case object Evil extends Orientation

}
