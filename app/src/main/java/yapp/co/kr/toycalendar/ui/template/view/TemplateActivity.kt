package yapp.co.kr.toycalendar.ui.template.view

import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import yapp.co.kr.toycalendar.R
import yapp.co.kr.toycalendar.base.BaseActivity
import yapp.co.kr.toycalendar.databinding.ActivityTemplateBinding
import yapp.co.kr.toycalendar.ui.template.viewModel.TemplateViewModel


class TemplateActivity : BaseActivity(){
    override val layoutRes = R.layout.activity_template
    override val isUseDatabinding = true
    lateinit var binding: ActivityTemplateBinding

    // var mCredential: GoogleAccountCredential? = null

    private val templateVM: TemplateViewModel by lazy {
        ViewModelProviders.of(this).get(TemplateViewModel::class.java)
    }

    override fun onDataBinding() {
        super.onDataBinding()
        binding = DataBindingUtil.setContentView(this, layoutRes)
        binding.templateVM = templateVM
    }

    // 일반 뷰 처리
    override fun setupViews() {
        transaction(R.id.fl_container, TemplateFragment())
    }

    // ViewModel observe 처리
    override fun subscribeUI() {
    }
}