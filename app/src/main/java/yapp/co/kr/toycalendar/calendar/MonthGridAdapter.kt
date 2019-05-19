package yapp.co.kr.toycalendar.calendar

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.LinearLayout
import android.widget.TextView
import yapp.co.kr.toycalendar.calendar.entity.Day
import yapp.co.kr.toycalendar.calendar.widget.DayView
import yapp.toycalendar.co.kr.toycalendar.R

class MonthGridAdapter(val context :Context) : BaseAdapter (){

    var days = listOf<Day>()
    private var dayViewHolder : DayViewHolder?=null

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var convertedView = convertView
        if (convertedView == null) {
            convertedView = DayView(context,days[position])
            dayViewHolder = DayViewHolder(convertedView)
            convertedView.tag = dayViewHolder
        } else {
            dayViewHolder = convertedView.tag as DayViewHolder
        }
//        val convertedView= convertView ?: inflater.inflate(R.layout.calendar_layout_day_view, parent, false)

        return convertedView
    }


    override fun getItem(position: Int) = days[position]

    override fun getItemId(position: Int) = position.toLong()

    override fun getCount() = days.size

    fun updateDays(updatedDays: List<Day>) {
        days = updatedDays
        notifyDataSetChanged()
    }

    internal inner class DayViewHolder(val dayView: DayView)
}