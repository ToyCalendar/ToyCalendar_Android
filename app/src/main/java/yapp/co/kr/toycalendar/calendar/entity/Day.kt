package yapp.co.kr.toycalendar.calendar.entity

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Day(val isEmpty :Boolean = true,
          val year: Int= 0,
          val month: Int =0,
          val day: Int =0,
          val dayOfWeek: Int = 0){

    var sexYn: Boolean = false
    var deviceUseYn: Boolean = false
    var secretInfoList: List<SecretInfo?> = emptyList()
    var physiologyCycleYn: Boolean = false
    var physiologyStartYn: Boolean = false
    var physiologyEndYn: Boolean = false
    var ovulationCycleYn: Boolean = false
    var ovulationDayYn: Boolean = false
    var ovulationStartYn: Boolean = false
    var ovulationEndYn: Boolean = false
    var isClicked: Boolean = false
}

/*
    NORMAL,// 아무날도 아님
    PHYSIOLOGY, // 생리일
    PHYSIOLOGY_IN_SCHEDULE, // 생리 예정일
    OVULATION, // 배란일
    OVULATION_IN_SCHEDULE, // 배란예정일
    RELATIONSHIP // 관계
    Secretion // 분비물
*/