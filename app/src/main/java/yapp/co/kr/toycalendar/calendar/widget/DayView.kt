package yapp.co.kr.toycalendar.calendar.widget

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import yapp.co.kr.toycalendar.calendar.entity.Day
import yapp.toycalendar.co.kr.toycalendar.R

class DayView : LinearLayout {
    lateinit var title: TextView
    lateinit var isRelationShip: ImageView
    lateinit var isSecretion: ImageView
    lateinit var itemView: LinearLayout


    constructor(context: Context, day: Day) : this(context, null, day)

    constructor(context: Context, attrs: AttributeSet?, day: Day) : this(context, attrs, 0, day)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int, day: Day) : super(context, attrs, defStyleAttr) {
        initViews(context, attrs, day)
    }

    private fun initViews(context: Context, attrs: AttributeSet?, day: Day) {
        itemView = LayoutInflater.from(context).inflate(R.layout.calendar_layout_day_view, this) as LinearLayout

        title = itemView.findViewById(R.id.day_title)
        isRelationShip = itemView.findViewById(R.id.heart)
        isSecretion = itemView.findViewById(R.id.black_dot)
        updateDay(day)

    }

    fun updateDay(day: Day) {
        if (day.isEmpty) {
            itemView.visibility = View.GONE
            return
        }
        title.text = "${day.day}"

        isRelationShip.visibility = if (day.sexYn) View.VISIBLE else View.GONE
        isSecretion.visibility = if (day.secretInfoList.isNotEmpty()) View.VISIBLE else View.GONE
    }


    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
    }

}