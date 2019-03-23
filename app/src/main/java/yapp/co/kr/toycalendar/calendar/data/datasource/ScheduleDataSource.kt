package yapp.co.kr.toycalendar.calendar.data.datasource

import io.reactivex.Single
import yapp.co.kr.toycalendar.calendar.entity.Schedule
import yapp.co.kr.toycalendar.calendar.entity.ScheduleResult
import java.util.*

interface ScheduleDataSource {
    fun getSchedule( monthList : List<String>) : Single<ScheduleResult>
}