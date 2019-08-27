package yapp.co.kr.toycalendar

import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import yapp.co.kr.toycalendar.calendar.MonthType
import yapp.co.kr.toycalendar.calendar.MonthViewModel
import yapp.co.kr.toycalendar.calendar.ViewModelProvider
import yapp.co.kr.toycalendar.calendar.entity.MonthData
import yapp.co.kr.toycalendar.calendar.widget.MonthView
import yapp.toycalendar.co.kr.toycalendar.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val monthView = findViewById<MonthView>(R.id.month_view)
        monthView.setViewModel(ViewModelProvider().getOrCreate(MonthData(2019, 5, MonthType.MyCalendar)))
        monthView.setOnDayClickListener{
            Toast.makeText(applicationContext,"${it.day}" +
                    "${it.ovulationCycleYn}"+
                    "${it.ovulationDayYn}"+
                    "${it.ovulationEndYn}"+
                    "${it.ovulationStartYn}"+
                    "${it.physiologyCycleYn}"+
                    "${it.physiologyEndYn}"+
                    "${it.physiologyStartYn}",Toast.LENGTH_SHORT).show()
        }
    }
}
