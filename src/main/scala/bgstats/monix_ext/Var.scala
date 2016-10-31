package bgstats.monix_ext

import monix.execution.Scheduler
import monix.reactive.Observable
import monix.reactive.Pipe

class Var[T](init: T)(implicit scheduler: Scheduler) {

  private[this] val (publishVar$, readVar$) = Pipe.replayLimited[T](1).multicast

  private[this] var value: T = init
  publishVar$.onNext(value)

  /**
    * Get an observable for the value.
    */
  val value$: Observable[T] = readVar$

  /**
    * Get the current value.
    */
  def get: T = value

  /**
    * Set the value. Automatically notifies all observers.
    */
  def set(t: T): Unit = {
    value = t
    publishVar$.onNext(value)
  }

  /**
    * Modify the value by applying a function. Automatically
    * notifies all observers.
    */
  def modify(f: T => T): Unit = {
    set(f(get))
  }

  /**
    * Convert [[Var]] to human-readable string.
    */
  override def toString: String =
    s"Var(value = [$value])"

}

object Var {

  def apply[T](init: T)(implicit scheduler: Scheduler): Var[T] = {
    new Var[T](init)
  }

}
