package yapp.co.kr.toycalendar.ui.login.viewModel

import android.app.Application
import androidx.databinding.ObservableField
import com.google.firebase.auth.FirebaseAuth
import io.reactivex.schedulers.Schedulers
import yapp.co.kr.toycalendar.base.BaseViewModel
import yapp.co.kr.toycalendar.base.SingleLiveEvent
import yapp.co.kr.toycalendar.calendar.data.CalendarRepositoryImpl
import yapp.co.kr.toycalendar.calendar.domain.usecase.GetSchedules
import yapp.co.kr.toycalendar.util.LoginType

class LoginViewModel(app: Application) : BaseViewModel(app) {
    var loginAction: SingleLiveEvent<LoginType> = SingleLiveEvent()
    var loginObservable: ObservableField<LoginType> = ObservableField()

    // 카카오 세팅 완료 시 더 이상 리스너를 붙이지 않습니다.
    var needKaKaoSetting = true

    var auth: FirebaseAuth? = null

    init {
        loginAction.value = null
        loginObservable.set(null)

        // GetSchedules(CalendarRepositoryImpl(), Schedulers.io()).invoke("",
        //         { list -> System.out.println(list) },
        //         { throwable -> System.out.println(throwable) },
        //         false)

        auth = FirebaseAuth.getInstance()
    }


    fun kakaoSettingFinished() {
        needKaKaoSetting = false
    }

    fun callAction(action: LoginType) {
        loginObservable.set(action)

        when (action) {
            LoginType.GOOGLE, LoginType.FACEBOOK, LoginType.DEFAULT -> {
                if (auth?.currentUser != null) {
                    // already signed in
                } else {
                    // not signed in
                }
            }
            LoginType.KAKAO -> {

            }
            LoginType.NONE -> {

            }
        }

        loginAction.value = action
    }
}