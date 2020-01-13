package yapp.co.kr.toycalendar.util.login.kakao

import android.util.Log
import com.kakao.auth.ISessionCallback
import com.kakao.network.ErrorResult
import com.kakao.usermgmt.UserManagement
import com.kakao.usermgmt.callback.MeV2ResponseCallback
import com.kakao.usermgmt.response.MeV2Response
import com.kakao.util.exception.KakaoException


class SessionCallback(kakaoInterface: KakaoInterface) : ISessionCallback {
    private var kakaoInterface = kakaoInterface
    // 로그인에 실패한 상태
    override fun onSessionOpenFailed(exception: KakaoException) {
        Log.e("SessionCallback :: ", "onSessionOpenFailed : " + exception.message)
    }

    // 로그인에 성공한 상태
    override fun onSessionOpened() {
        requestMe()
    }

    // 사용자 정보 요청
    fun requestMe() {
        // 사용자정보 요청 결과에 대한 Callback
        UserManagement.getInstance().me(object : MeV2ResponseCallback() {
            // 세션 오픈 실패. 세션이 삭제된 경우,
            override fun onSessionClosed(errorResult: ErrorResult) {
                Log.e("SessionCallback :: ", "onSessionClosed : " + errorResult.errorMessage)
            }

            // 사용자정보 요청에 성공한 경우,
            override fun onSuccess(response: MeV2Response) {
                Log.e("SessionCallback :: ", "onSuccess")

                val nickname = response.nickname
                val email = response.kakaoAccount.email ?: response.id.toString()
                val profileImagePath = response.profileImagePath
                val thumnailPath = response.thumbnailImagePath
                val id = response.id

                Log.e("Profile : ", nickname + "")
                Log.e("Profile : ", email + "")
                Log.e("Profile : ", profileImagePath + "")
                Log.e("Profile : ", thumnailPath + "")
                Log.e("Profile : ", id.toString() + "")

                kakaoInterface.afterGetInfo(response)
            }

            // 사용자 정보 요청 실패
            override fun onFailure(errorResult: ErrorResult?) {
                Log.e("SessionCallback :: ", "onFailure : " + errorResult!!.errorMessage)
            }
        })
    }
}