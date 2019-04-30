package yapp.co.kr.toycalendar.ui.login.viewModel

import android.app.Application
import com.google.firebase.auth.FirebaseAuth
import io.reactivex.schedulers.Schedulers
import yapp.co.kr.toycalendar.base.BaseViewModel
import yapp.co.kr.toycalendar.calendar.data.CalendarRepositoryImpl
import yapp.co.kr.toycalendar.calendar.domain.usecase.GetSchedules

class LoginViewModel(app: Application) : BaseViewModel(app) {
    var auth : FirebaseAuth? = null
    init {
        GetSchedules(CalendarRepositoryImpl(), Schedulers.io()).invoke("",
                { list -> System.out.println(list) },
                { throwable -> System.out.println(throwable) },
                false)

        auth = FirebaseAuth.getInstance()
    }

    override fun callAction(action: String) {
        when (action) {
            "google" -> {
                if (auth?.currentUser != null) {
                    // already signed in
                } else {
                    // not signed in
                }
            }
        }

        this.action.value = action
    }
}