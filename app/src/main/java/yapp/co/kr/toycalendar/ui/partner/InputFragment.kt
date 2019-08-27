package yapp.co.kr.toycalendar.ui.partner

import android.view.View
import yapp.co.kr.toycalendar.R
import yapp.co.kr.toycalendar.base.BaseFragment
import yapp.co.kr.toycalendar.databinding.FragmentInputBinding

class InputFragment: BaseFragment(){
    override val layoutRes = R.layout.fragment_input
    override val isUseDataBinding = true
    lateinit var binding : FragmentInputBinding
    // val inputVM : InputBindi

    override fun setupViews(view: View) {
    }

    override fun subscribeUI() {

    }
}