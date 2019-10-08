package yapp.co.kr.toycalendar

import android.content.Context
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import yapp.co.kr.toycalendar.util.LoginType

// LoginActivity
@BindingAdapter("android:background")
fun setLoginBtnBackground(view : ConstraintLayout, type: LoginType) {
    when (type) {
        LoginType.KAKAO -> {
            view.setBackgroundResource(R.drawable.btn_login_button_kakao)
        }
        LoginType.FACEBOOK -> {
            view.setBackgroundResource(R.drawable.btn_login_button_facebook)
        }
        LoginType.GOOGLE -> {
            view.setBackgroundResource(R.drawable.btn_login_button_google)
        }
        LoginType.DEFAULT -> {
            view.setBackgroundResource(R.drawable.btn_login_button_default)
        }
    }
}

@BindingAdapter("android:src")
fun setLoginLogo(view : ImageView, type: LoginType) {
    when (type) {
        LoginType.KAKAO -> {
            view.setImageResource(R.drawable.btn_login_button_kakao)
        }
        LoginType.FACEBOOK -> {
            view.setImageResource(R.drawable.btn_login_button_facebook)
        }
        LoginType.GOOGLE -> {
            view.setImageResource(R.drawable.btn_login_button_google)
        }
        LoginType.DEFAULT -> {
            view.visibility = View.GONE
        }
    }
}

@BindingAdapter("android:text")
fun setLoginText(view : TextView, type: LoginType) {
    val sb = StringBuilder()
    val ctx = view.context

    when (type) {
        LoginType.KAKAO -> {
            view.setTextColor(ContextCompat.getColor(ctx, R.color.btn_login_button_text_color_kakao))
            sb.append("카카오톡으로 시작하기")
        }
        LoginType.FACEBOOK -> {
            view.setTextColor(ContextCompat.getColor(ctx, R.color.btn_login_button_text_color_facebook))
            sb.append("페이스북으로 시작하기")
        }
        LoginType.GOOGLE -> {
            view.setTextColor(ContextCompat.getColor(ctx, R.color.btn_login_button_text_color_google))
            sb.append("구글로 시작하기")
        }
        LoginType.DEFAULT -> {
            view.setTextColor(ContextCompat.getColor(ctx, R.color.btn_login_button_text_color_default))
            sb.append("이메일로 가입하기")
        }
    }

    view.text = sb.toString()
}