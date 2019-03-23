package yapp.co.kr.toycalendar

import android.app.Application

class App : Application(){

    companion object {

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
    }
}