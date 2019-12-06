package yapp.co.kr.toycalendar.ui.login.view

import android.accounts.Account
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.IdpResponse
import com.google.android.gms.auth.GoogleAuthUtil
import com.google.android.gms.common.Scopes
import com.kakao.auth.AuthType
import com.kakao.auth.Session
import com.kakao.usermgmt.response.MeV2Response
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.addTo
import io.reactivex.schedulers.Schedulers
import yapp.co.kr.toycalendar.MainActivity
import yapp.co.kr.toycalendar.R
import yapp.co.kr.toycalendar.base.BaseActivity
import yapp.co.kr.toycalendar.databinding.ActivityLoginBinding
import yapp.co.kr.toycalendar.ui.login.viewModel.LoginViewModel
import yapp.co.kr.toycalendar.util.LoginType
import yapp.co.kr.toycalendar.util.login.facebook.parseJSONObject
import yapp.co.kr.toycalendar.util.login.kakao.KakaoInterface
import yapp.co.kr.toycalendar.util.login.kakao.SessionCallback
import java.util.*


class LoginActivity : BaseActivity(), KakaoInterface {
    override val layoutRes = R.layout.activity_login
    override val isUseDatabinding = true
    lateinit var binding: ActivityLoginBinding

    // facebook
    var callbackManager: CallbackManager? = null

    // kakao
    var session: Session? = null
    var callback: SessionCallback? = null

    private val loginVM: LoginViewModel by lazy {
        ViewModelProviders.of(this).get(LoginViewModel::class.java)
    }

    companion object {
        val RC_SIGN_IN = 123

        const val TAG = "LoginActivity : "
    }

    // Choose authentication googleProviders
    val googleProviders = Arrays.asList(
            AuthUI.IdpConfig.GoogleBuilder().build())
    val facebookProviders = Arrays.asList(
            AuthUI.IdpConfig.FacebookBuilder().build())
    val emailProviders = Arrays.asList(
            AuthUI.IdpConfig.EmailBuilder().build())

    override fun afterGetInfo(response: MeV2Response) {
        session?.removeCallback(callback)
        // TODO 서버 요청 로직 진행
        Toast.makeText(this, session?.tokenInfo?.accessToken!!, Toast.LENGTH_SHORT).show()
        // loginVM.getAccessToken(session?.tokenInfo?.accessToken!!, LoginType.KAKAO)
    }

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
        loginVM.loginAction.observe(this, Observer { action ->
            when (action) {
                LoginType.GOOGLE -> {
                    startActivityForResult(
                            AuthUI.getInstance()
                                    .createSignInIntentBuilder()
                                    .setAvailableProviders(googleProviders)
                                    .setIsSmartLockEnabled(false)
                                    .build(), RC_SIGN_IN)

//                    // 오류 콜백 확인용
//                    AuthUI.getInstance().silentSignIn(this, googleProviders)
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
                LoginType.FACEBOOK -> {
                    callbackManager = CallbackManager.Factory.create()
                    LoginManager.getInstance()
                            .logInWithReadPermissions(this, Arrays.asList("public_profile", "email"))

                    LoginManager.getInstance().registerCallback(callbackManager,
                            object : FacebookCallback<LoginResult> {
                                override fun onSuccess(loginResult: LoginResult) {
                                    Log.d(
                                            "MainActivity",
                                            "Facebook token: " + loginResult.accessToken.token
                                    )

                                    Toast.makeText(this@LoginActivity, loginResult.accessToken.token, Toast.LENGTH_SHORT).show()

                                    parseJSONObject(loginResult)
                                }

                                override fun onCancel() {
                                    Log.d("MainActivity", "Facebook onCancel.")
                                    callbackManager = null
                                }

                                override fun onError(error: FacebookException) {
                                    Log.d("MainActivity", "Facebook onError.")
                                    callbackManager = null
                                }
                            })
                }

                LoginType.KAKAO -> {
                    if (loginVM.needKaKaoSetting) {
                        loginVM.kakaoSettingFinished()
                        session = Session.getCurrentSession()
                        callback = SessionCallback(this)
                        session?.addCallback(callback)
                    }
                    session?.open(AuthType.KAKAO_LOGIN_ALL, this)
                }

                LoginType.DEFAULT -> {
                    startActivityForResult(
                            AuthUI.getInstance()
                                    .createSignInIntentBuilder()
                                    .setAvailableProviders(emailProviders)
                                    .setIsSmartLockEnabled(false)
                                    .build(), RC_SIGN_IN)
                }
                LoginType.NONE -> {
                    startActivity(Intent(this, MainActivity::class.java))
                    finish()
                }
                else -> {
                }
            }
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        // must kakao login1
        if (Session.getCurrentSession().handleActivityResult(requestCode, resultCode, data)) {
            return
        }

        super.onActivityResult(requestCode, resultCode, data)

        // must facebook login1
        callbackManager?.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN) {
            val response = IdpResponse.fromResultIntent(data)

            if (resultCode == Activity.RESULT_OK) {
                // Successfully signed in
                val user = loginVM.auth?.currentUser

                val account = Account(user?.email, "com.google")

                val disposable = Single.create<String> {
                    val scope = "oauth2:" + Scopes.EMAIL + " " + Scopes.PROFILE
                    val accessToken = GoogleAuthUtil.getToken(applicationContext, account, scope, Bundle())
                    Log.d(TAG, "accessToken : $accessToken") //accessToken:ya29.Gl...
                    it.onSuccess(accessToken)
                }
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe { message ->
                            Toast.makeText(applicationContext, message, Toast.LENGTH_SHORT).show()
                        }

                disposable.addTo(compositeDisposable)
            } else {
                println(response?.error?.errorCode)
            }
        }
    }
}