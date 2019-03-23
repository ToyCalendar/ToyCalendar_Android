package yapp.co.kr.toycalendar.calendar.domain.repository

import io.reactivex.Scheduler
import io.reactivex.Single
import io.reactivex.disposables.Disposable
import yapp.co.kr.toycalendar.calendar.entity.Schedule
import yapp.co.kr.toycalendar.calendar.entity.ScheduleResult
import java.util.*

interface CalendarRepository {
    fun getSchedules(force : Boolean, monthList : List<String>, scheduler : Scheduler, success: (scheduleResult: ScheduleResult) -> Unit, error: (throwable: Throwable) -> Unit) : Disposable
}