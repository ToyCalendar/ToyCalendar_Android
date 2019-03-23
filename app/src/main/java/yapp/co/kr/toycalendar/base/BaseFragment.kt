package yapp.co.kr.toycalendar.base

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment

abstract class BaseFragment : Fragment() {
    @get:LayoutRes
    abstract val layoutRes: Int
    open val isUseDataBinding: Boolean = false
    var activity: BaseActivity? = null
    lateinit var dialog: BaseDialog

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        if (layoutRes != 0) {
            return if (!isUseDataBinding) {
                inflater.inflate(layoutRes, container, false)
            } else {
                onDataBinding(inflater, container)
            }
        }
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    open fun onDataBinding(inflater: LayoutInflater, container: ViewGroup?): View? {
        return null
    }

    // TODO 토의 내용1. onAttach / onDetach 작업을 Base에서 진행해주는 것이 맞는가?
    override fun onAttach(context: Context) {
        super.onAttach(context)

        activity = getActivity() as BaseActivity
        afterOnAttach(context)
    }

    // attach 이후 작업할 내용
    open fun afterOnAttach(context: Context){

    }

    override fun onDetach() {
        super.onDetach()
        activity = null

        afterOnDetach()
    }

    // detach 이후 작업할 내용
    open fun afterOnDetach(){

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dialog = BaseDialog(view.context)

        setupViews(view)
        subscribeUI()
    }

    abstract fun setupViews(view: View)

    abstract fun subscribeUI()

    // 다이얼로그 띄워주기
    fun openDialog(
        type: Int,
        message: String,
        layoutRes: Int,
        okListener: View.OnClickListener = View.OnClickListener {
            dialog.dismiss()
        },
        cancelListener: View.OnClickListener = View.OnClickListener {
            dialog.dismiss()
        }
    ) {
        dialog.setInit(layoutRes, type)
        dialog.setTitle(message)
        dialog.setOkButtonListener(okListener)
        dialog.setCancelButtonListener(cancelListener)

        dialog.callFunction()
    }
}