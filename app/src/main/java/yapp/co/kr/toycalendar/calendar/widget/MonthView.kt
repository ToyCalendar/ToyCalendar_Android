package yapp.co.kr.toycalendar.calendar.widget

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import yapp.co.kr.toycalendar.R
import yapp.co.kr.toycalendar.calendar.BaseCalendar
import yapp.co.kr.toycalendar.calendar.DaysUpdatedEvent
import yapp.co.kr.toycalendar.calendar.MonthGridAdapter
import yapp.co.kr.toycalendar.calendar.MonthViewModel
import yapp.co.kr.toycalendar.calendar.entity.Day

class MonthView : LinearLayout, BaseCalendar {

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        initViews(context, attrs)
    }

    private lateinit var headerTitle: TextView
    private lateinit var daysView: RecyclerView
    private var monthViewModel: MonthViewModel? = null
    private var monthGridAdapter: MonthGridAdapter? = null

    private var disposables: CompositeDisposable? = null
    private fun Disposable.addToDisposables() {
        (disposables ?: CompositeDisposable().apply { disposables = this }).add(this)
    }


    private fun initViews(context: Context, attrs: AttributeSet?) {
        clipToPadding = false
        clipChildren = false

        attrs?.let {

        }
        LayoutInflater.from(context).inflate(R.layout.calendar_layout_month_view, this)
        monthGridAdapter = MonthGridAdapter(context)
        headerTitle = findViewById(R.id.header_title)

        daysView = findViewById<RecyclerView>(R.id.days_view).apply {
            layoutManager = GridLayoutManager(context, 7, RecyclerView.VERTICAL, false)
            adapter = monthGridAdapter
        }
    }

    fun setViewModel(monthViewModel: MonthViewModel) {
        this.monthViewModel = monthViewModel
        headerTitle.text = monthViewModel.monthData.month.toString()
        updateViews()
    }

    fun clearViewModel() {
        monthViewModel = null
        disposables?.dispose()
        disposables = null
    }

    private fun updateViews() {
        monthViewModel?.let {
            it.daysUpdateEvent.subscribe(this::onDaysUpdateEvent).addToDisposables()
            it.getDays()
        }
    }


    override fun setOnDayClickListener(onClick: (Day) -> Unit) {
        monthGridAdapter?.onDayClickedEvent?.subscribe {
            onClick(it.day)
        }?.addToDisposables()
    }

    private fun onDaysUpdateEvent(daysUpdateEvent: DaysUpdatedEvent) {
        monthGridAdapter?.updateDays(daysUpdateEvent.days)
    }
}