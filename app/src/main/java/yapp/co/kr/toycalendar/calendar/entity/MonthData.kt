package yapp.co.kr.toycalendar.calendar.entity

import yapp.co.kr.toycalendar.calendar.MonthType

class MonthData(val year : Int, val month : Int, val monthType: MonthType) {
    val key = "$year-$month-$monthType"

    override fun equals(other: Any?): Boolean {
        return other is MonthData && key == other.key
    }

    override fun hashCode(): Int {
        return key.hashCode()
    }
}