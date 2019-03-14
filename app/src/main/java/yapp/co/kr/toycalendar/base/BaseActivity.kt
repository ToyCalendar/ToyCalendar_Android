package buv.co.kr.base

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.FragmentTransaction
import yapp.toycalendar.co.kr.toycalendar.R

abstract class BaseActivity : AppCompatActivity() {
    abstract val layoutRes: Int
    abstract val isUseDatabinding: Boolean
    lateinit var dialog: BaseDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (layoutRes != 0) {
            if (!isUseDatabinding)
                setContentView(layoutRes)
            else
                onDataBinding()
        }

        setupViews()
        subscribeUI()
    }

    open fun onDataBinding() {

    }

    // 뷰 컴포넌트 설정
    abstract fun setupViews()

    // livedata 있는 경우 observe 설정
    abstract fun subscribeUI()

    // 툴바 세팅
    fun setupToolbar(backgroundColor: Int) {
        // 1. 툴바 있는지 확인하여 있으면 세팅 (toolbar id 이름은 toolbar로 세팅)
        findViewById<Toolbar>(R.id.toolbar)?.apply {
            // 1. 툴바 백그라운드 설정
            setBackgroundResource(backgroundColor)

            // 2. actionbar 설정
            setSupportActionBar(this)
            supportActionBar!!.apply {
                setDisplayShowTitleEnabled(false)
                setDisplayHomeAsUpEnabled(false)
            }
        }
    }

    // 다이얼로그 설정
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
        // 초기 설정
        dialog.setInit(layoutRes, type)

        //
        dialog.setTitle(message)
        dialog.setOkButtonListener(okListener)
        dialog.setCancelButtonListener(cancelListener)

        dialog.callFunction()
    }

    // fragment가 있을 경우 transacion 진행
    fun transaction(targetId : Int, newFragment: BaseFragment) {
        val transaction = supportFragmentManager.beginTransaction()

        // Replace whatever is in the fragment_container view with this fragment,
        // and add the transaction to the back stack so the user can navigate back
        transaction.replace(targetId, newFragment)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)

        // transaction.addToBackStack(null)

        // Commit the transaction
        transaction.commit()
    }
}