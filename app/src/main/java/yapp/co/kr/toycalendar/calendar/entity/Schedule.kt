package yapp.co.kr.toycalendar.calendar.entity

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.util.*

//Date Format = yyyy-MM-dd
data class Schedule(
        @Expose @SerializedName("scheduleId") val scheduleId : Int,
        @Expose @SerializedName("scheduleType") val scheduleType: ScheduleType,
        @Expose @SerializedName("startDate") val startDate : String,
        @Expose @SerializedName("endDate")val endDate : String)

enum class ScheduleType {

    NORMAL,// 아무날도 아님
    PHYSIOLOGY, // 생리일
    PHYSIOLOGY_IN_SCHEDULE, // 생리 예정일
    OVULATION, // 배란일
    OVULATION_IN_SCHEDULE, // 배란예정일
    RELATIONSHIP, // 관계 정보
    SECRETION;// 분비 정보
}

enum class ScheduleDrawType{
    NONE,
    START,
    PROCEEDING,
    END
}