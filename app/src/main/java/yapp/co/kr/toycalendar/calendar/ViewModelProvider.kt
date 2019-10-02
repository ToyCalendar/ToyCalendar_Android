package yapp.co.kr.toycalendar.calendar

import yapp.co.kr.toycalendar.calendar.entity.MonthData

class ViewModelProvider{
    private val monthViewmodels = hashMapOf<String,MonthViewModel>()
    fun getOrCreate(monthData : MonthData) =
        monthViewmodels[monthData.key] ?: MonthViewModel(monthData).apply{ monthViewmodels[monthData.key] = this }
}