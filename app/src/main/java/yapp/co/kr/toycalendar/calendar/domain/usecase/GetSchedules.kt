package yapp.co.kr.toycalendar.calendar.domain.usecase

import io.reactivex.Scheduler
import io.reactivex.disposables.Disposable
import yapp.co.kr.toycalendar.calendar.MonthType
import yapp.co.kr.toycalendar.calendar.domain.repository.CalendarRepository
import yapp.co.kr.toycalendar.calendar.entity.Schedule

class GetSchedules(private val calendarRepository: CalendarRepository, private val scheduler: Scheduler) : RxUseCase() {
    operator fun invoke(monthList: List<String>, type: MonthType, success: (schedules: List<Schedule?>?) -> Unit, error: (throwable: Throwable) -> Unit, force: Boolean = false) : Disposable {
       return calendarRepository.getSchedules(force, monthList, scheduler, {
            when (type) {
                MonthType.PartnerCalendar -> success(it.data?.partnerSchedules)
                MonthType.MyCalendar -> success(it.data?.mySchedules)
            }
        }, error)
    }

    operator fun invoke(month: String, type: MonthType, success: (schedules: List<Schedule?>?) -> Unit, error: (throwable: Throwable) -> Unit, force: Boolean = false) : Disposable {
        return calendarRepository.getSchedules(force, listOf(month), scheduler, {
            when (type) {
                MonthType.PartnerCalendar -> success(it.data?.partnerSchedules)
                MonthType.MyCalendar -> success(it.data?.mySchedules)
            }        }, error)
    }
}