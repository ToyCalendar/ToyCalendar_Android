package yapp.co.kr.toycalendar.ui.login.view

import android.app.Activity
import android.content.Intent
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.IdpResponse
import yapp.co.kr.toycalendar.R
import yapp.co.kr.toycalendar.base.BaseActivity
import yapp.co.kr.toycalendar.databinding.ActivityLoginBinding
import yapp.co.kr.toycalendar.ui.login.viewModel.LoginViewModel
import java.util.*


class LoginActivity : BaseActivity() {
    override val layoutRes = R.layout.activity_login
    override val isUseDatabinding = true
    lateinit var binding: ActivityLoginBinding

    // var mCredential: GoogleAccountCredential? = null

    private val loginVM: LoginViewModel by lazy {
        ViewModelProviders.of(this).get(LoginViewModel::class.java)
    }

    companion object {
        val RC_SIGN_IN = 123
    }

    // Choose authentication providers
    val providers = Arrays.asList(
            AuthUI.IdpConfig.GoogleBuilder().build())

    override fun onDataBinding() {
        super.onDataBinding()
        binding = DataBindingUtil.setContentView(this, layoutRes)
        binding.loginVM = loginVM
    }

    // 일반 뷰 처리
    override fun setupViews() {

    }

    // ViewModel observe 처리
    override fun subscribeUI() {
        loginVM.action.observe(this, Observer { action ->
            when (action) {
                "google" -> {
                    startActivityForResult(
                            AuthUI.getInstance()
                                    .createSignInIntentBuilder()
                                    .setAvailableProviders(providers)
                                    .setIsSmartLockEnabled(false)
                                    .build(), RC_SIGN_IN)

//                    // 오류 콜백 확인용
//                    AuthUI.getInstance().silentSignIn(this, providers)
//                            .addOnCompleteListener(this) { task ->
//                                if (task.isSuccessful) {
//                                    Toast.makeText(this, "뮻ㅇ", Toast.LENGTH_SHORT).show()
//                                } else {
//                                    Toast.makeText(this, "sign_in_failed", Toast.LENGTH_SHORT).show()
//                                }
//                            }

                    // loginVM.releaseClick()
                    // finish()
                }

                "facebook" -> {

                }
            }
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN) {
            val response = IdpResponse.fromResultIntent(data)

            if (resultCode == Activity.RESULT_OK) {
                // Successfully signed in
                val user = loginVM.auth?.currentUser

                Toast.makeText(this, response?.idpToken, Toast.LENGTH_SHORT).show()
            } else {
                // Sign in failed. If response is null the user canceled the
                // sign-in flow using the back button. Otherwise check
                // response.getError().getErrorCode() and handle the error.
                // ...
                System.out.println(response?.error?.errorCode)
            }
        }
    }
}