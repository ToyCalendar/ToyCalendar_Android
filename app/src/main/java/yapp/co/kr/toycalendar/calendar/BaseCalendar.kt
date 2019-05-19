package yapp.co.kr.toycalendar.calendar

import yapp.co.kr.toycalendar.calendar.entity.Day

interface BaseCalendar {
    fun setOnDayClickListener(onClick:(Day) -> Unit)
}