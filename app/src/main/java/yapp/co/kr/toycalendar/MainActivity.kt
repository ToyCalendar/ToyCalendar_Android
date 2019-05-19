package yapp.co.kr.toycalendar

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import yapp.co.kr.toycalendar.calendar.MonthType
import yapp.co.kr.toycalendar.calendar.MonthViewModel
import yapp.co.kr.toycalendar.calendar.widget.MonthView
import yapp.toycalendar.co.kr.toycalendar.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val monthView = findViewById<MonthView>(R.id.month_view)
        monthView.setViewModel(MonthViewModel(2019, 5, MonthType.MyCalendar))
    }
}
