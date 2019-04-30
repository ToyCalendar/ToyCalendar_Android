package yapp.co.kr.toycalendar.ui.template.view

import android.content.Intent
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import yapp.co.kr.toycalendar.base.BaseActivity
import yapp.co.kr.toycalendar.ui.login.view.LoginActivity
import yapp.co.kr.toycalendar.ui.template.viewModel.TemplateViewModel
import yapp.co.kr.toycalendar.R
import yapp.co.kr.toycalendar.databinding.ActivityTemplateBinding

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
        templateVM.action.observe(this, Observer {action ->
            when(action){
                "next" -> {
                    var intent = Intent(this@TemplateActivity, LoginActivity::class.java)
                    startActivity(intent)

                    // TODO 토의 내용2_2. 클릭 중복 방지를 이렇게 처리해주는 게 옳은 걸까?
                    templateVM.releaseClick()
                    finish()
                }
            }
        })
    }
}