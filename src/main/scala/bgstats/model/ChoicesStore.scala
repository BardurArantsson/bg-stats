package bgstats.model

import monifu.reactive.Observable

trait ChoicesStore extends ChoicesCommands {

  val inputChoices$: Observable[Choices]

}
