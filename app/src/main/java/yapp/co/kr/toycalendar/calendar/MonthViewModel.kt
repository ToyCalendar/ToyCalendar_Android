package yapp.co.kr.toycalendar.calendar

import io.reactivex.android.schedulers.AndroidSchedulers
import yapp.co.kr.toycalendar.calendar.data.CalendarRepositoryImpl
import yapp.co.kr.toycalendar.calendar.domain.usecase.GetSchedules
import yapp.co.kr.toycalendar.calendar.entity.Day
import yapp.co.kr.toycalendar.calendar.entity.Schedule
import java.text.SimpleDateFormat
import java.util.*

class MonthViewModel(val year: Int,val month: Int, val type : MonthType){
    private val calendar = GregorianCalendar(year, month -1, 1)
    private val monthFormat = SimpleDateFormat("yyyyMMdd",Locale.getDefault()).format(calendar.time)

    private val getSchedules = GetSchedules(CalendarRepositoryImpl(), AndroidSchedulers.mainThread())

    val daysUpdateEvent: ToyRxEvent<DaysUpdatedEvent> = ToyRxEvent.create()

    fun getDays() {
        getSchedules(monthFormat,type,{
            daysUpdateEvent.send(DaysUpdatedEvent(createDays(it)))
        },{

        })
    }

    private fun createDays(schedules: List<Schedule?>?): List<Day> {
        schedules ?: return emptyList()

        val list = mutableListOf<Day>()

        //SunDay : 1 ... SATURDAY : 7
        val firstDayOfMonth = calendar.get(Calendar.DAY_OF_WEEK) - 1 // 0~6

        val numOfDays = calendar.getActualMaximum(Calendar.DAY_OF_MONTH)

        var day = 1
        var dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK)

        //create Days
        for (i in 0 until numOfDays) {
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val date = SimpleDateFormat("yyyyMMdd",Locale.getDefault()).format(GregorianCalendar(year,month,day).time)
            list.add(createDay(year,month, day, dayOfWeek, schedules.findLast { it?.date.equals(date) }))
            day++
            dayOfWeek++
            if (dayOfWeek > 7) dayOfWeek -= 7
        }

        //create Before EmptyDays
        for (i in 0 until firstDayOfMonth) list.add(0,Day(0,0,0,0,true).apply{
            if(list[0].physiologyEndYn || list[0].physiologyCycleYn) physiologyCycleYn = true
            if(list[0].ovulationEndYn || list[0].ovulationCycleYn) ovulationCycleYn = true
        })

        //create After EmptyDays
        list.last().dayOfWeek.let {
            for (i in 0 until 7 - it) list.add(Day(0,0,0,0,true).apply{
                if(list[list.lastIndex].physiologyStartYn || list[list.lastIndex].physiologyCycleYn) physiologyCycleYn = true
                if(list[list.lastIndex].ovulationStartYn || list[list.lastIndex].ovulationCycleYn) ovulationCycleYn = true
            })
        }
        return list
    }

    //해당 날에 기타 정보들 입력.
    private fun createDay(year: Int, month: Int, day: Int, dayOfWeek: Int, schedule: Schedule?): Day {
        val result = Day(year, month, day, dayOfWeek)
        schedule?: return result

        result.sexYn = schedule.sexYn?: false
        result.deviceUseYn = schedule.deviceUseYn?: false
        result.secretInfoList = schedule.secretInfoList?: emptyList()
        result.physiologyCycleYn = schedule.physiologyCycleYn?: false
        result.physiologyStartYn = schedule.physiologyStartYn?: false
        result.physiologyEndYn = schedule.physiologyEndYn?: false
        result.ovulationDayYn = schedule.ovulationDayYn?: false
        result.ovulationStartYn = schedule.ovulationStartYn?: false
        result.ovulationEndYn = schedule.ovulationEndYn?: false

        return result
    }

}

enum class MonthType{
    MyCalendar,
    PartnerCalendar
}