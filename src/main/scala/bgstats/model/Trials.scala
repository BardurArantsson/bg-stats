package bgstats.model

import bgstats.model.Orientation._

case class Trials(
  wrath: Orientation,
  fear: Orientation,
  greed: Orientation,
  pride: Orientation,
  selfish: Orientation)

object Trials {

  /**
   * Default values for the Hell trial choices.
   */
  def default: Trials = Trials(
    wrath = Good,
    fear = Good,
    selfish = Good,
    greed = Good,
    pride = Good)

}
