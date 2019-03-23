package yapp.co.kr.toycalendar.ui.template.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import yapp.co.kr.toycalendar.base.BaseFragment
import yapp.co.kr.toycalendar.ui.template.viewModel.TemplateFragmentViewModel
import yapp.toycalendar.co.kr.toycalendar.R

class TemplateFragment : BaseFragment() {
    override val layoutRes: Int = R.layout.fragment_template
    override val isUseDataBinding: Boolean = true
    private val templateFragmentVM : TemplateFragmentViewModel by lazy {
        ViewModelProviders.of(this).get(TemplateFragmentViewModel::class.java)
    }
    private lateinit var binding: yapp.toycalendar.co.kr.toycalendar.databinding.FragmentTemplateBinding

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
        templateFragmentVM.action.observe(this, Observer {action ->
            Toast.makeText(activity, action, Toast.LENGTH_SHORT).show()
            templateFragmentVM.releaseClick()
        })
    }
}