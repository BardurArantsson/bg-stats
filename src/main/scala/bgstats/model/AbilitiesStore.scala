package bgstats.model

import monifu.reactive.Observable

trait AbilitiesStore extends AbilitiesCommands {

  val baseAbilities$: Observable[Abilities]

}
