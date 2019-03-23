package yapp.co.kr.toycalendar.calendar.entity

class Day(val year: Int,
          val month: Int,
          val day: Int,
          val dayOfWeek: Int){
    var physiology: ScheduleDrawType = ScheduleDrawType.NONE
    var physiologyInSchedule: ScheduleDrawType = ScheduleDrawType.NONE
    var ovulation: ScheduleDrawType = ScheduleDrawType.NONE
    var ovulationInSchedule: ScheduleDrawType= ScheduleDrawType.NONE
    var isRelationShip : Boolean =false
    var isSecretion : Boolean = false
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