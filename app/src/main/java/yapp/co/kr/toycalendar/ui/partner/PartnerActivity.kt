package yapp.co.kr.toycalendar.ui.partner

import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import yapp.co.kr.toycalendar.R
import yapp.co.kr.toycalendar.base.BaseActivity
import yapp.co.kr.toycalendar.databinding.ActivityPartnerBinding
import yapp.co.kr.toycalendar.ui.partner.ext.replaceFragmentInActivity

class PartnerActivity : BaseActivity() {
    override val layoutRes = R.layout.activity_partner
    override val isUseDatabinding = true
    lateinit var partnerBinding: ActivityPartnerBinding

    val partnerVM: PartnerViewModel by lazy {
        ViewModelProviders.of(this).get(PartnerViewModel::class.java)
    }

    override fun onDataBinding() {
        super.onDataBinding()
        partnerBinding = DataBindingUtil.setContentView(this, layoutRes)
        partnerBinding.activity = this
        partnerBinding.partnerVM = partnerVM
    }

    override fun setupViews() {
        replaceFragmentInActivity(InputFragment(), R.id.fl_partner)
    }

    override fun subscribeUI() {

    }
}