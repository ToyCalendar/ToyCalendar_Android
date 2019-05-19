package yapp.co.kr.toycalendar.calendar.widget

import android.app.PendingIntent.getActivity
import android.content.Context
import android.content.res.TypedArray
import android.graphics.Canvas
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import yapp.co.kr.toycalendar.calendar.entity.Day
import yapp.toycalendar.co.kr.toycalendar.R


class DayView : LinearLayout {
    private lateinit var title: TextView
    private lateinit var isRelationShip: ImageView
    private lateinit var isSecretion: ImageView
    private lateinit var itemView: LinearLayout
    private lateinit var mDay: Day


    constructor(context: Context, day: Day) : this(context, null, day)

    constructor(context: Context, attrs: AttributeSet?, day: Day) : this(context, attrs, 0, day)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int, day: Day) : super(context, attrs, defStyleAttr) {
        initViews(context, attrs, day)
    }

    private fun initViews(context: Context, attrs: AttributeSet?, day: Day) {
        itemView = (LayoutInflater.from(context).inflate(R.layout.calendar_layout_day_view, this) as LinearLayout)
        mDay = day
        title = itemView.findViewById(yapp.toycalendar.co.kr.toycalendar.R.id.day_title)
        isRelationShip = itemView.findViewById(yapp.toycalendar.co.kr.toycalendar.R.id.heart)
        isSecretion = itemView.findViewById(yapp.toycalendar.co.kr.toycalendar.R.id.black_dot)
        updateDay(day)

    }

    private fun updateDay(day: Day) {
        if (day.isEmpty) {
            itemView.visibility = View.GONE
            return
        }
        title.text = "${day.day}"

        isRelationShip.visibility = if (day.sexYn) View.VISIBLE else View.GONE
        isSecretion.visibility = if (day.secretInfoList.isNotEmpty()) View.VISIBLE else View.GONE
    }

    fun getDay() = mDay

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        return super.dispatchTouchEvent(ev)

    }

    fun setOnDayClickListener(onClicked : (Day) -> Unit){
        itemView.setOnClickListener{
            onClicked(mDay)
        }
    }


    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
    }

}