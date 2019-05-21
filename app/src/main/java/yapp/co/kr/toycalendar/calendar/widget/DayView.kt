package yapp.co.kr.toycalendar.calendar.widget

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import yapp.co.kr.toycalendar.calendar.entity.Day
import yapp.toycalendar.co.kr.toycalendar.R


class DayView : LinearLayout {
    private lateinit var title: TextView
    private lateinit var isRelationShip: ImageView
    private lateinit var isSecretion: ImageView
    private lateinit var itemView: LinearLayout
    private lateinit var mDay: Day
    private var circleSize = 0f
    private var type1Color = Paint().apply {
        color = Color.parseColor("#ff5354")

    }
    private var type2Color = Paint().apply {
        color = Color.parseColor("#6147fb")
    }
    private var type3Color = Paint().apply {
        color = Color.parseColor("#000000")
    }

    constructor(context: Context, day: Day) : this(context, null, day)

    constructor(context: Context, attrs: AttributeSet?, day: Day) : this(context, attrs, 0, day)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int, day: Day) : super(context, attrs, defStyleAttr) {
        initViews(context, attrs, day)
    }

    private fun initViews(context: Context, attrs: AttributeSet?, day: Day) {
        itemView = (LayoutInflater.from(context).inflate(R.layout.calendar_layout_day_view, this) as LinearLayout)
        title = itemView.findViewById(yapp.toycalendar.co.kr.toycalendar.R.id.day_title)
        isRelationShip = itemView.findViewById(yapp.toycalendar.co.kr.toycalendar.R.id.heart)
        isSecretion = itemView.findViewById(yapp.toycalendar.co.kr.toycalendar.R.id.black_dot)
        updateDay(day)
        setWillNotDraw(false) // For Draw in Viewgroup
    }

    fun updateDay(day: Day) {
        mDay =day
        if (mDay.isEmpty && !mDay.ovulationCycleYn && !mDay.physiologyCycleYn) {
                itemView.visibility = View.GONE
            return
        }

        itemView.visibility = View.VISIBLE
        title.text = if(day.day ==0) "" else "${day.day}"
        isRelationShip.visibility = if (day.sexYn) View.VISIBLE else View.GONE
        isSecretion.visibility = if (day.secretInfoList.isNotEmpty()) View.VISIBLE else View.GONE
    }

    fun getDay() = mDay

    fun setOnDayClickListener(onClicked: (Day) -> Unit) {
        if (mDay.isEmpty) {
            itemView.setOnClickListener(null)
            return
        }

        val typedArray = context.obtainStyledAttributes(intArrayOf(R.attr.selectableItemBackground))
        itemView.setBackgroundResource(typedArray.getResourceId(0, 0))
        itemView.setOnClickListener {
            onClicked(mDay)
        }
    }


    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        circleSize = (title.bottom - title.top) / 2.toFloat()

        if (mDay.physiologyStartYn || mDay.physiologyEndYn || mDay.physiologyCycleYn || mDay.ovulationStartYn || mDay.ovulationEndYn || mDay.ovulationCycleYn || mDay.isClicked) {
            title.setTextColor(ContextCompat.getColor(context, R.color.white))
            when {
                mDay.physiologyStartYn -> {
                    canvas?.drawCircle(width / 2.toFloat(), (title.top + title.bottom) / 2.toFloat(), circleSize, type1Color)
                    canvas?.drawRect(Rect(width / 2, title.top, width, title.bottom), type1Color)
                }
                mDay.physiologyEndYn -> {
                    canvas?.drawCircle(width / 2.toFloat(), (title.top + title.bottom) / 2.toFloat(), circleSize, type1Color)
                    canvas?.drawRect(Rect(0, title.top, width / 2, title.bottom), type1Color)

                }
                mDay.physiologyCycleYn -> {
                    canvas?.drawRect(Rect(0, title.top, width, title.bottom), type1Color)

                }
                mDay.ovulationStartYn -> {
                    canvas?.drawCircle(width / 2.toFloat(), (title.top + title.bottom) / 2.toFloat(), circleSize, type2Color)
                    canvas?.drawRect(Rect(width / 2, title.top, width, title.bottom), type2Color)
                }
                mDay.ovulationEndYn -> {
                    canvas?.drawCircle(width / 2.toFloat(), (title.top + title.bottom) / 2.toFloat(), circleSize, type2Color)
                    canvas?.drawRect(Rect(0, title.top, width / 2, title.bottom), type2Color)
                }
                mDay.ovulationCycleYn -> {
                    canvas?.drawRect(Rect(0, title.top, width, title.bottom), type2Color)
                }
            }

            if (mDay.isClicked) {
                canvas?.drawCircle(width / 2.toFloat(), (title.top + title.bottom) / 2.toFloat(), circleSize - 6, type3Color)
            }
        }else{
            title.setTextColor(ContextCompat.getColor(context, R.color.black))
        }


    }

}