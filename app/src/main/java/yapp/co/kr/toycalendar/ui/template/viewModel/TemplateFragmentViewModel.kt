package yapp.co.kr.toycalendar.ui.template.viewModel

import android.app.Application
import androidx.databinding.ObservableField
import yapp.co.kr.toycalendar.base.BaseViewModel

class TemplateFragmentViewModel(app: Application) : BaseViewModel(app) {
    var tv_hello_index : ObservableField<Int>? = null
    var tv_hello_text = "0"

    init {
        tv_hello_index = ObservableField()
        tv_hello_index!!.set(0)
    }

    fun setText(){
        var newIndex = tv_hello_index?.get()!! + 1
        tv_hello_text += "$newIndex"
        tv_hello_index?.set(newIndex)

        callAction("setText")
    }

}