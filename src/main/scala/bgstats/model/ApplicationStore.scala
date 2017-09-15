package bgstats.model

import monix.reactive.Observable

trait ApplicationStore[M] extends AllCommands {

  val state$: Observable[ApplicationState[M]]

}
