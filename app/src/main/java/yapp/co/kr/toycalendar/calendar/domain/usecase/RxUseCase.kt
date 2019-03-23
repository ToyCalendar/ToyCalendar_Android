package yapp.co.kr.toycalendar.calendar.domain.usecase

import io.reactivex.disposables.Disposable

abstract class RxUseCase {
    protected var disposable: Disposable? = null
    fun cancel() {
        disposable?.dispose()
        disposable = null
    }
}