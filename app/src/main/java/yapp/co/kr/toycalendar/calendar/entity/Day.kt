package yapp.co.kr.toycalendar.calendar.entity

class Day(val isEmpty :Boolean = true,
          val year: Int= 0,
          val month: Int =0,
          val day: Int =0,
          val dayOfWeek: Int = 0,
          val type : DayType = DayType.NONE){

    var sexYn: Boolean = false
    var deviceUseYn: Boolean = false
    var secretInfoList: List<SecretInfo?> = emptyList()
    var isClicked: Boolean = false
}

enum class DayType{
    NONE,// 아무날도 아님
    PHYSIOLOGY_START, // 생리일
    PHYSIOLOGY_CYCLE,
    PHYSIOLOGY_END,
    OVULATION_START, // 배란일
    OVULATION_DAY, // 배란일
    OVULATION_CYCLE,
    OVULATION_END,
}
/*
    NONE,// 아무날도 아님
    PHYSIOLOGY, // 생리일
    PHYSIOLOGY_IN_SCHEDULE, // 생리 예정일
    OVULATION, // 배란일
    OVULATION_IN_SCHEDULE, // 배란예정일
    RELATIONSHIP // 관계
    Secretion // 분비물
*/