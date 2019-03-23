package yapp.co.kr.toycalendar.calendar.data

import io.reactivex.Scheduler
import io.reactivex.disposables.Disposable
import yapp.co.kr.toycalendar.calendar.data.datasource.ScheduleMockDatasource
import yapp.co.kr.toycalendar.calendar.domain.repository.CalendarRepository
import yapp.co.kr.toycalendar.calendar.entity.ScheduleResult
import java.util.*

class CalendarRepositoryImpl : CalendarRepository {
    val mockDataSource = ScheduleMockDatasource()


    override fun getSchedules(force: Boolean, monthList: List<String>, scheduler: Scheduler, success: (scheduleResult: ScheduleResult) -> Unit, error: (throwable: Throwable) -> Unit): Disposable {
        return mockDataSource.getSchedule(monthList).subscribeOn(scheduler).subscribe({
            success(it)
        }, {
            error(it)
        })
    }
}