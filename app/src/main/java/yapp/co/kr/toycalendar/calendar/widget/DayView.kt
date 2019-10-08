package yapp.co.kr.toycalendar.calendar.widget

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import yapp.co.kr.toycalendar.R
import yapp.co.kr.toycalendar.calendar.entity.Day
import yapp.co.kr.toycalendar.calendar.entity.DayType.*


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

    private var circleSize = resources.getDimension(R.dimen.calendar_circle_width)
    private var ovulationPaint = Paint().apply {
        strokeWidth = resources.getDimension(R.dimen.calendar_circle_line_width)
    }
    private var physiologyPaint = Paint().apply {
        style = Paint.Style.STROKE
        strokeWidth = resources.getDimension(R.dimen.calendar_circle_line_width)
        color = ContextCompat.getColor(context, R.color.calendar_day_physiology_background)
    }
    private var selectedPaint = Paint().apply {
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
        if (mDay.isEmpty) {
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

        when (mDay.type) {
            NONE -> {
                title.setTextColor(ContextCompat.getColor(context, R.color.calendar_day_text))
            }
            PHYSIOLOGY_START, PHYSIOLOGY_CYCLE, PHYSIOLOGY_END -> {
                //TODO 생리예정이 아니라 생리중일 떈 색칠해야하니 잠시 대기

//                    rect.set(width / 2, title.top, width, title.bottom)
//                    canvas?.drawRect(rect, ovulationPaint)
//                    rect.set(0, title.top, width / 2, title.bottom)
//                    canvas?.drawRect(rect, ovulationPaint)
                canvas?.drawCircle(
                    width / 2.toFloat(),
                    (title.top + title.bottom) / 2.toFloat(),
                    circleSize,
                    physiologyPaint
                )
                title.setTextColor(
                    ContextCompat.getColor(
                        context,
                        R.color.calendar_day_physiology_text
                    )
                )
            }
            OVULATION_DAY -> {
                ovulationPaint.style = Paint.Style.FILL
                ovulationPaint.color = ContextCompat.getColor(
                    context,
                    R.color.calendar_day_ovulation_background_with_opacity
                )

                canvas?.drawCircle(
                    width / 2.toFloat(),
                    (title.top + title.bottom) / 2.toFloat(),
                    circleSize,
                    ovulationPaint
                )
                title.setTextColor(
                    ContextCompat.getColor(
                        context,
                        R.color.calendar_day_ovulation_text
                    )
                )
            }
            OVULATION_START, OVULATION_CYCLE, OVULATION_END -> {
                ovulationPaint.style = Paint.Style.STROKE
                ovulationPaint.color = ContextCompat.getColor(
                    context,
                    R.color.calendar_day_ovulation_background
                )

                canvas?.drawCircle(
                    width / 2.toFloat(),
                    (title.top + title.bottom) / 2.toFloat(),
                    circleSize,
                    ovulationPaint
                )
                title.setTextColor(
                    ContextCompat.getColor(
                        context,
                        R.color.calendar_day_ovulation_text
                    )
                )
            }
        }


        if (mDay.isClicked) {
            canvas?.drawCircle(
                width / 2.toFloat(),
                (title.top + title.bottom) / 2.toFloat(),
                circleSize,
                selectedPaint
            )
            title.setTextColor(
                ContextCompat.getColor(
                    context,
                    R.color.calendar_day_selected_text
                )
            )
        }
    }
}