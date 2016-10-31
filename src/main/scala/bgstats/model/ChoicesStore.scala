package bgstats.model

import monix.reactive.Observable

trait ChoicesStore extends ChoicesCommands {

  val inputChoices$: Observable[Choices]

}
