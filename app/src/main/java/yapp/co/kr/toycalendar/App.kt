package yapp.co.kr.toycalendar

import android.app.Application
import com.facebook.FacebookSdk
import com.facebook.appevents.AppEventsLogger
import com.kakao.auth.KakaoSDK
import yapp.co.kr.toycalendar.util.login.kakao.KakaoSDKAdapter

class App : Application(){

    companion object {

        // ThreadSafe
        @Volatile
        private var app: App? = null

        fun getApp(): App? {
            assert(app != null)
            return app
        }
    }

    override fun onCreate() {
        app = this
        super.onCreate()

        // must kakao login2
        KakaoSDK.init(KakaoSDKAdapter())

        // must facebook login2
        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);
    }
}