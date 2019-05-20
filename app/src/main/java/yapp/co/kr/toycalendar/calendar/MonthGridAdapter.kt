package yapp.co.kr.toycalendar.calendar

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import yapp.co.kr.toycalendar.calendar.entity.Day
import yapp.co.kr.toycalendar.calendar.widget.DayView

class MonthGridAdapter(val context :Context) : BaseAdapter (){

    private var days = listOf<Day>()
    private var dayViewHolder : DayViewHolder?=null
    val onDayClicked: ToyRxEvent<DayClickedEvent> = ToyRxEvent.create()

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
        dayViewHolder?.onBind()

        return convertedView
    }


    override fun getItem(position: Int) = days[position]

    override fun getItemId(position: Int) = position.toLong()

    override fun getCount() = days.size

    fun updateDays(updatedDays: List<Day>) {
        days = updatedDays
        notifyDataSetChanged()
    }
    internal inner class DayViewHolder(val dayView: DayView){
        fun onBind(){
            dayView.setOnDayClickListener{
                onDayClicked.send(DayClickedEvent(it))
                onDayClicked(it)
            }
        }
    }
    private fun onDayClicked(day: Day){
        days.forEach{
            it.isClicked = it == day
        }
        notifyDataSetChanged()
    }
}