package yapp.co.kr.toycalendar.calendar.widget

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.graphics.Typeface
import android.util.AttributeSet
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import yapp.co.kr.toycalendar.R
import yapp.co.kr.toycalendar.calendar.entity.Day


class DayView : LinearLayout {
    private lateinit var title: TextView
    private lateinit var isRelationShip: ImageView
    private lateinit var isSecretion: ImageView
    private lateinit var itemView: LinearLayout
    private lateinit var mDay: Day
    private var circleSize = 0f
    private var type1Color = Paint().apply {
        typeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD)
        textSize = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 11F, context.resources.displayMetrics)
        color = Color.parseColor("#ff5354")

    }
    private var type2Color = Paint().apply {
        typeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD)
        textSize = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 11F, context.resources.displayMetrics)
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
        clipToPadding = false
        clipChildren = false
        itemView = (LayoutInflater.from(context).inflate(R.layout.calendar_layout_day_view, this) as LinearLayout)
        title = itemView.findViewById(R.id.day_title)
        isRelationShip = itemView.findViewById(R.id.heart)
        isSecretion = itemView.findViewById(R.id.black_dot)
        updateDay(day)
        disableParentsClip(itemView)
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
                    canvas?.drawText("생리기간",width / 2f,title.top-15.toFloat(),type1Color)
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
                    canvas?.drawText("배란기간",width / 2f,title.top-15.toFloat(),type2Color)
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
                canvas?.drawCircle(width / 2.toFloat(), (title.top + title.bottom) / 2.toFloat(), circleSize - 10, type3Color)
            }
        }else{
            title.setTextColor(ContextCompat.getColor(context, R.color.black))
        }


    }
    fun disableParentsClip(view: View) {
        var view = view
        while (view.parent != null && view.parent is ViewGroup) {
            val viewGroup = view.parent as ViewGroup
            viewGroup.clipChildren = false
            viewGroup.clipToPadding = false
            view = viewGroup
        }
    }
}