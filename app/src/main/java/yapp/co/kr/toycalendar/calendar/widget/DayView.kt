package yapp.co.kr.toycalendar.calendar.widget

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import yapp.co.kr.toycalendar.R
import yapp.co.kr.toycalendar.calendar.entity.Day


class DayView : LinearLayout {

    constructor(context: Context, day: Day) : this(context, null, day)

    constructor(context: Context, attrs: AttributeSet?, day: Day) : this(context, attrs, 0, day)

    constructor(
        context: Context,
        attrs: AttributeSet?,
        defStyleAttr: Int,
        day: Day
    ) : super(context, attrs, defStyleAttr) {
        initViews(context, attrs, day)
    }

    private lateinit var title: TextView
    private lateinit var isRelationShip: ImageView
    private lateinit var isSecretion: ImageView
    private lateinit var itemView: LinearLayout
    private lateinit var mDay: Day

    private val rect = Rect()
    private var circleSize = resources.getDimension(R.dimen.calendar_circle_width)
    private var ovulationColor = Paint().apply {
        style = Paint.Style.STROKE
        strokeWidth = resources.getDimension(R.dimen.calendar_circle_line_width)
        color = ContextCompat.getColor(context, R.color.calendar_day_ovulation_background)
    }
    private var physiologyColor = Paint().apply {
        style = Paint.Style.STROKE
        strokeWidth = resources.getDimension(R.dimen.calendar_circle_line_width)
        color = ContextCompat.getColor(context, R.color.calendar_day_physiology_background)
    }
    private var selectedColor = Paint().apply {
        color = ContextCompat.getColor(context, R.color.calendar_day_selected_background)
    }

    private fun initViews(context: Context, attrs: AttributeSet?, day: Day) {
        itemView = (LayoutInflater.from(context).inflate(
            R.layout.calendar_layout_day_view,
            this
        ) as LinearLayout)
        title = itemView.findViewById(R.id.day_title)
        isRelationShip = itemView.findViewById(R.id.heart)
        isSecretion = itemView.findViewById(R.id.black_dot)
        updateDay(day)
        setWillNotDraw(false) // For Draw in Viewgroup
    }

    fun updateDay(day: Day) {
        mDay = day
        if (mDay.isEmpty && !mDay.ovulationCycleYn && !mDay.physiologyCycleYn) {
            itemView.visibility = View.GONE
            return
        }

        itemView.visibility = View.VISIBLE
        title.text = if (day.day == 0) "" else "${day.day}"
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

        if (mDay.physiologyStartYn || mDay.physiologyEndYn || mDay.physiologyCycleYn || mDay.ovulationStartYn || mDay.ovulationEndYn || mDay.ovulationCycleYn || mDay.isClicked) {

            when {
                //생리
                mDay.physiologyStartYn || mDay.physiologyEndYn || mDay.physiologyCycleYn -> {
                    canvas?.drawCircle(
                        width / 2.toFloat(),
                        (title.top + title.bottom) / 2.toFloat(),
                        circleSize,
                        physiologyColor
                    )
                    title.setTextColor(physiologyColor.color)

                }

                //TODO 생리예정이 아니라 생리중일 떈 색칠해야하니 잠시 대기

//                    rect.set(width / 2, title.top, width, title.bottom)
//                    canvas?.drawRect(rect, ovulationColor)
//                    rect.set(0, title.top, width / 2, title.bottom)
//                    canvas?.drawRect(rect, ovulationColor)
                // 배란
                mDay.ovulationStartYn || mDay.ovulationEndYn || mDay.ovulationCycleYn -> {
                    canvas?.drawCircle(
                        width / 2.toFloat(),
                        (title.top + title.bottom) / 2.toFloat(),
                        circleSize,
                        ovulationColor
                    )
                    title.setTextColor(ovulationColor.color)
                }
            }

            if (mDay.isClicked) {
                canvas?.drawCircle(
                    width / 2.toFloat(),
                    (title.top + title.bottom) / 2.toFloat(),
                    circleSize - 10,
                    selectedColor
                )
            }
        } else {
            title.setTextColor(ContextCompat.getColor(context, R.color.black))
        }


    }
}