package bgstats.model

import monix.reactive.Observable

trait AbilitiesStore extends AbilitiesCommands {

  val baseAbilities$: Observable[Abilities]

}
