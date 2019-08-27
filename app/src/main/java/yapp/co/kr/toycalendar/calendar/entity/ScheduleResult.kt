package yapp.co.kr.toycalendar.calendar.entity

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class ScheduleResult(
        @Expose @SerializedName("code") val code: Int?,
        @Expose @SerializedName("message") val message: String?,
        @Expose @SerializedName("data") val data: ScheduleData?
)

data class ScheduleData(
        @Expose @SerializedName("mySelf") val mySchedules: List<Schedule?>?,
        @Expose @SerializedName("partner") val partnerSchedules: List<Schedule?>?
)

data class Schedule(
        @Expose @SerializedName("date") val date: String?,
        @Expose @SerializedName("sexYn") val sexYn: Boolean?,
        @Expose @SerializedName("deviceUseYn") val deviceUseYn: Boolean?,
        @Expose @SerializedName("secretInfoList") val secretInfoList: List<SecretInfo?>?,
        @Expose @SerializedName("physiologyCycleYn") val physiologyCycleYn: Boolean?,
        @Expose @SerializedName("physiologyStartYn") val physiologyStartYn: Boolean?,
        @Expose @SerializedName("physiologyEndYn") val physiologyEndYn: Boolean?,
        @Expose @SerializedName("ovulationCycleYn") val ovulationCycleYn: Boolean?,
        @Expose @SerializedName("ovulationDayYn") val ovulationDayYn: Boolean?,
        @Expose @SerializedName("ovulationStartYn") val ovulationStartYn: Boolean?,
        @Expose @SerializedName("ovulationEndYn") val ovulationEndYn: Boolean?,
        @Expose @SerializedName("dayPoint") val dayPoint: Int?
)

data class SecretInfo(
        @Expose @SerializedName("secretInfoCode") val secretInfoCode: String,
        @Expose @SerializedName("secretInfoName") val secretInfoName: String
)