package yapp.co.kr.toycalendar.calendar

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import androidx.recyclerview.widget.RecyclerView
import yapp.co.kr.toycalendar.calendar.entity.Day
import yapp.co.kr.toycalendar.calendar.widget.DayView

class MonthGridAdapter(val context : Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var days = listOf<Day>()
    val onDayClickedEvent: ToyRxEvent<DayClickedEvent> = ToyRxEvent.create()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return DayViewHodler(DayView(parent.context,Day()))
    }

    override fun getItemCount() = days.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if(holder is DayViewHodler){
            holder.onBind(days[position],this::onDayClicked)
        }
    }


    fun getItem(position: Int) = days[position]

    override fun getItemId(position: Int) = position.toLong()

    fun updateDays(updatedDays: List<Day>) {
        days = updatedDays
        notifyDataSetChanged()
    }

    private fun onDayClicked(day: Day){
        onDayClickedEvent.send(DayClickedEvent(day))

        days.forEach{
            it.isClicked = it == day
        }
        notifyDataSetChanged()
    }
}

class DayViewHodler(itemView: View) : RecyclerView.ViewHolder(itemView ) {
    fun onBind(day : Day, onClicked : (Day) -> Unit){
        if(itemView is DayView){
            itemView.updateDay(day)
            itemView.setOnDayClickListener{
                onClicked(it)
            }
        }
    }
}
