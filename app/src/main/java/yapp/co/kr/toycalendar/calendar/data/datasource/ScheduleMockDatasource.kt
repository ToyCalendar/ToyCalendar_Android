package yapp.co.kr.toycalendar.calendar.data.datasource

import com.google.gson.Gson
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import yapp.co.kr.toycalendar.App
import yapp.co.kr.toycalendar.calendar.entity.Schedule
import yapp.co.kr.toycalendar.calendar.entity.ScheduleResult
import java.io.InputStreamReader
import java.text.SimpleDateFormat
import java.util.*

class ScheduleMockDatasource : ScheduleDataSource {

    private val scheduleResultFileName = "test_schedule_dummy.json"
    override fun getSchedule(monthList: List<String>): Single<ScheduleResult> {
        return Single.create<ScheduleResult> {
            val isr = InputStreamReader(App.getApp()!!.assets.open(scheduleResultFileName))
            val result = Gson().fromJson(isr,ScheduleResult::class.java)
            it.onSuccess(result)
        }.subscribeOn(Schedulers.io())
    }

    private fun getFilteredInfo(monthList: List<String>, scheduleResult: ScheduleResult): ScheduleResult {
        val list = mutableListOf<Schedule>()
        val monthDateFormat = SimpleDateFormat("yyyy-MM")
        val scheduleDateFormat = SimpleDateFormat("yyyy-MM-dd")

        scheduleResult.schedules.forEach { schedule ->
            for (s in monthList) {
                val beforeDate = monthDateFormat.parse(s)
                val afterDate = Calendar.getInstance().apply {
                    time = beforeDate
                    add(Calendar.MONTH, 1)
                }.time

                val startDate = scheduleDateFormat.parse(schedule.startDate)
                val endDate = scheduleDateFormat.parse(schedule.endDate)

                if ((endDate.after(beforeDate) && endDate.before(afterDate)) || (startDate.after(beforeDate) && startDate.before(afterDate))) {
                    list.add(schedule)
                    break
                }
            }
        }
        return ScheduleResult(list)
    }
}