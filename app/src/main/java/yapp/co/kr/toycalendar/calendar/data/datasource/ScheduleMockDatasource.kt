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
}