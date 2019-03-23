package yapp.co.kr.toycalendar.calendar.entity

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class ScheduleResult(
        @Expose @SerializedName("schedules") val schedules: List<Schedule>
)