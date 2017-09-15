package bgstats.model

import monix.reactive.Observable

class ApplicationStoreImpl[M](
  breakpoints: Seq[Breakpoint[M]],
  abilitiesStore: AbilitiesStore,
  choicesStore: ChoicesStore) extends ApplicationStore[M] {

  override val state$: Observable[ApplicationState[M]] =
    abilitiesStore.baseAbilities$.combineLatest(
      choicesStore.inputChoices$).map({
        case (baseAbilities, choices) =>
          ApplicationState(baseAbilities, choices)(breakpoints)
        })

  override def updateChoices(choices: Choices): Unit =
    choicesStore.updateChoices(choices)

  override def updateAbilities(abilities: Abilities): Unit =
    abilitiesStore.updateAbilities(abilities)

}
