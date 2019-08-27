package yapp.co.kr.toycalendar.ui.template.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import yapp.co.kr.toycalendar.R
import yapp.co.kr.toycalendar.base.BaseFragment
import yapp.co.kr.toycalendar.databinding.FragmentTemplateBinding
import yapp.co.kr.toycalendar.ui.template.viewModel.TemplateFragmentViewModel

class TemplateFragment : BaseFragment() {
    override val layoutRes: Int = R.layout.fragment_template
    override val isUseDataBinding: Boolean = true
    private val templateFragmentVM : TemplateFragmentViewModel by lazy {
        ViewModelProviders.of(this).get(TemplateFragmentViewModel::class.java)
    }
    private lateinit var binding: FragmentTemplateBinding

    override fun onDataBinding(inflater: LayoutInflater, container: ViewGroup?): View? {
        binding = DataBindingUtil.inflate(inflater, layoutRes, container, false)
        binding.templateFragmentVM = templateFragmentVM
        binding.setLifecycleOwner(this)
        return binding.root
    }

    override fun setupViews(view: View) {
        view.apply {

        }
    }

    override fun subscribeUI() {
    }
}