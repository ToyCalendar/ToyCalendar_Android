package yapp.co.kr.toycalendar.calendar.domain.usecase

import io.reactivex.Scheduler
import yapp.co.kr.toycalendar.calendar.MonthType
import yapp.co.kr.toycalendar.calendar.domain.repository.CalendarRepository
import yapp.co.kr.toycalendar.calendar.entity.Schedule

class GetSchedules(val calendarRepository: CalendarRepository, val scheduler: Scheduler) : RxUseCase() {
    operator fun invoke(monthList: List<String>, type: MonthType, success: (schedules: List<Schedule?>?) -> Unit, error: (throwable: Throwable) -> Unit, force: Boolean = false) {
        disposable = calendarRepository.getSchedules(force, monthList, scheduler, {
            when (type) {
                MonthType.PartnerCalendar -> success(it.data?.partnerSchedules)
                MonthType.MyCalendar -> success(it.data?.mySchedules)
            }
        }, error)
    }

    operator fun invoke(month: String, type: MonthType, success: (schedules: List<Schedule?>?) -> Unit, error: (throwable: Throwable) -> Unit, force: Boolean = false) {
        disposable = calendarRepository.getSchedules(force, listOf(month), scheduler, {
            when (type) {
                MonthType.PartnerCalendar -> success(it.data?.partnerSchedules)
                MonthType.MyCalendar -> success(it.data?.mySchedules)
            }        }, error)
    }
}