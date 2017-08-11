package bgstats.model

import bgstats.model.ApplicationStore.State
import monix.reactive.Observable

class ApplicationStoreImpl[M](
  breakpoints: Seq[Breakpoint[M]],
  abilitiesStore: AbilitiesStore,
  choicesStore: ChoicesStore) extends ApplicationStore[M] {

  override val state$: Observable[State[M]] =
    abilitiesStore.baseAbilities$.combineLatest(
      choicesStore.inputChoices$).map({
        case (baseAbilities, choices) =>
          State(baseAbilities, choices)(breakpoints)
        })

  override def updateChoices(choices: Choices): Unit =
    choicesStore.updateChoices(choices)

  override def updateAbilities(abilities: Abilities): Unit =
    abilitiesStore.updateAbilities(abilities)

}
