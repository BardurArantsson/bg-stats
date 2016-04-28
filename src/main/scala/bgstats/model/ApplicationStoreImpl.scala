package bgstats.model

import bgstats.model.AbilityColumn.DreamColumn
import bgstats.model.AbilityColumn.HellColumn
import bgstats.model.AbilityColumn.MachineColumn
import bgstats.model.AbilityColumn.TomesColumn
import bgstats.model.ApplicationStore.State
import monifu.concurrent.Scheduler
import monifu.reactive.Observable

import scalaz.Monoid
import scalaz.syntax.monoid._

class ApplicationStoreImpl[M](
  breakpoints: Seq[Breakpoint[M]],
  abilitiesStore: AbilitiesStore,
  choicesStore: ChoicesStore)(
  implicit scheduler: Scheduler) extends ApplicationStore[M] {

  private[this] val deltaColumns$: Observable[Vector[(AbilityColumn, Effects)]] =
    choicesStore.inputChoices$.map { choices =>
      Vector(
        DreamColumn -> choices.dreamEffects,
        HellColumn -> choices.trialsEffects,
        MachineColumn -> choices.machineEffects,
        TomesColumn -> choices.bg1TomeEffects)
    }

  private[this] val deltaTotal$: Observable[Effects] =
    deltaColumns$.map { cs =>
      cs.map(_._2).foldLeft(Monoid[Effects].zero)((a,b) => a |+| b)
    }

  private[this] val baseSummaryAbilities$: Observable[SummaryAbilities] =
    abilitiesStore.baseAbilities$.map { a =>
      SummaryAbilities(abilities = a, magicResistance = 0)
    }

  private[this] val totals$: Observable[SummaryAbilities] =
    baseSummaryAbilities$.combineLatest(deltaTotal$) map { case (base, delta) =>
      base |+| delta.deltaSummaryAbilities
    }

  private[this] val renderedBreakpoints$: Observable[Seq[RenderedBreakpoint[M]]] = {
    totals$.map(_.abilities).map { (a: Abilities) =>
      breakpoints.map(breakpoint =>
        RenderedBreakpoint(breakpoint, achieved = a.values.getOrElse(breakpoint.ability, 0) >= breakpoint.minimum))
    }
  }

  override val state$: Observable[State[M]] =
    baseSummaryAbilities$.combineLatest(
      choicesStore.inputChoices$.combineLatest(
        renderedBreakpoints$.combineLatest(
          deltaColumns$.combineLatest(
            deltaTotal$.combineLatest(totals$))))).map({ case (a, (b, (c, (d, (e, f))))) =>
      State(
        baseSummaryAbilities = a,
        inputChoices = b,
        renderedBreakpoints = c,
        deltaColumns = d,
        deltaTotal = e,
        totalsColumn = f)
    })

  override def updateChoices(choices: Choices): Unit =
    choicesStore.updateChoices(choices)

  override def updateAbilities(abilities: Abilities): Unit =
    abilitiesStore.updateAbilities(abilities)

}
