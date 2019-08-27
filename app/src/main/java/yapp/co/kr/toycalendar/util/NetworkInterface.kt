package yapp.co.kr.toycalendar.util

import io.reactivex.Single
import retrofit2.http.Header
import retrofit2.http.POST

open interface NetworkInterface {
    // 토큰의 유효성을 체크 - 완료
    @POST("/login")
    fun postLogin(
        @Header("sns") sns: String,
        @Header("key") key: String): Single<LoginType>

}