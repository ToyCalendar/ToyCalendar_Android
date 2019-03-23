package yapp.co.kr.toycalendar.calendar

import io.reactivex.Scheduler
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Consumer
import io.reactivex.subjects.PublishSubject

class ToyRxEvent<T> private constructor() {

    companion object {
        fun <T> create(): ToyRxEvent<T> = ToyRxEvent()
    }

    private var rxEvent: PublishSubject<T> = PublishSubject.create()

    val hasObservers: Boolean get() = rxEvent.hasObservers()

    fun send(event: T) {
        rxEvent.onNext(event)
    }

    //out of parentheses
    fun subscribe(consumer: (event: T) -> Unit) = subscribe(consumer, null)

    fun subscribe(consumer: (event: T) -> Unit, scheduler: Scheduler? = null): Disposable {
        return if (scheduler == null) {
            rxEvent.subscribe(Consumer(consumer), ToyErrorConsumer)
        } else {
            rxEvent.observeOn(scheduler).subscribe(Consumer(consumer), ToyErrorConsumer)
        }
    }

    fun subscribe(predicate: (event: T) -> Boolean, consumer: (event: T) -> Unit) = subscribe(predicate, consumer, null)
    fun subscribe(predicate: (event: T) -> Boolean, consumer: (event: T) -> Unit, scheduler: Scheduler? = null): Disposable {
        return if (scheduler == null) {
            rxEvent.filter(predicate).subscribe(Consumer(consumer), ToyErrorConsumer)
        } else {
            rxEvent.observeOn(scheduler).filter(predicate).subscribe(Consumer(consumer), ToyErrorConsumer)
        }
    }

    fun clear() {
        rxEvent.onComplete()
        rxEvent = PublishSubject.create()
    }
}

object ToyErrorConsumer : Consumer<Throwable> {
    override fun accept(t: Throwable?) {
        //Something
    }
}
