package yapp.co.kr.toycalendar.calendar.domain.usecase

import io.reactivex.Scheduler
import yapp.co.kr.toycalendar.calendar.domain.repository.CalendarRepository
import yapp.co.kr.toycalendar.calendar.entity.Schedule

class GetSchedules(val calendarRepository: CalendarRepository, val scheduler: Scheduler) : RxUseCase() {
    operator fun invoke(monthList : List<String>, success: (schedules: List<Schedule>) -> Unit, error: (throwable: Throwable) -> Unit, force: Boolean = false) {
        disposable = calendarRepository.getSchedules(force,monthList, scheduler, {
            success(it.schedules)
        }, error)
    }

    operator fun invoke(monthList : String, success: (schedules: List<Schedule>) -> Unit, error: (throwable: Throwable) -> Unit, force: Boolean = false) {
        disposable = calendarRepository.getSchedules(force, listOf(monthList), scheduler, {
            success(it.schedules)
        }, error)
    }
}