package yapp.co.kr.toycalendar.calendar

import yapp.co.kr.toycalendar.calendar.entity.Day

sealed class ToyCalendarEvent

data class DaysUpdatedEvent(val days: List<Day>) : ToyCalendarEvent()
data class DayClickedEvent(val day: Day) : ToyCalendarEvent()
