package yapp.co.kr.toycalendar.ui.partner

import android.app.Application
import androidx.databinding.ObservableField
import yapp.co.kr.toycalendar.R
import yapp.co.kr.toycalendar.base.BaseViewModel

class InputViewModel(app: Application) : BaseViewModel(app) {
    var message = ObservableField<String>()
    var code = ObservableField<String>()
    var btnClickEnabled = ObservableField<Boolean>()

    init {
        message.set(app.getString(R.string.partner_init))
    }


    fun checkCode() {
        btnClickEnabled.set(Integer.parseInt(code.get() ?: "0") > 99999)
    }
}