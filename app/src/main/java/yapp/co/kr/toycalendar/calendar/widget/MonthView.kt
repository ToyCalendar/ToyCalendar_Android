package yapp.co.kr.toycalendar.calendar.widget

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.GridView
import android.widget.LinearLayout
import android.widget.TextView
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import yapp.co.kr.toycalendar.calendar.DaysUpdatedEvent
import yapp.co.kr.toycalendar.calendar.MonthGridAdapter
import yapp.co.kr.toycalendar.calendar.MonthViewModel
import yapp.co.kr.toycalendar.calendar.BaseCalendar
import yapp.co.kr.toycalendar.calendar.entity.Day
import yapp.toycalendar.co.kr.toycalendar.R

class MonthView : LinearLayout , BaseCalendar {

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        initViews(context, attrs)
    }

    lateinit var headerTitle: TextView
    lateinit var daysView: GridView
    private var monthViewModel : MonthViewModel?= null
    private var monthGridAdapter : MonthGridAdapter? = null

    private var disposables: CompositeDisposable? = null
    private fun Disposable.addToDisposables() {
        (disposables ?: CompositeDisposable().apply { disposables = this }).add(this)
    }


    private fun initViews(context: Context, attrs: AttributeSet?) {
        attrs?.let {

        }
        LayoutInflater.from(context).inflate(R.layout.calendar_layout_month_view, this)
        monthGridAdapter = MonthGridAdapter(context)
        headerTitle = findViewById(R.id.header_title)
        daysView = findViewById(R.id.days_view)
        daysView.adapter = monthGridAdapter
    }

    fun setViewModel(monthViewModel: MonthViewModel){
        this.monthViewModel = monthViewModel
        updateViews()
    }
    fun clearViewModel(){
        monthViewModel = null
        disposables?.dispose()
        disposables = null
    }

    private fun updateViews(){
        monthViewModel?.let{
            it.daysUpdateEvent.subscribe (this::onDaysUpdateEvent).addToDisposables()
            it.getDays()
        }
    }


    override fun setOnDayClickListener(onClick: (Day) -> Unit) {
        monthGridAdapter?.onDayClicked?.subscribe{
            onClick(it.day)
        }?.addToDisposables()
    }

    private fun onDaysUpdateEvent(daysUpdateEvent : DaysUpdatedEvent){
        monthGridAdapter?.updateDays(daysUpdateEvent.days)
    }
}