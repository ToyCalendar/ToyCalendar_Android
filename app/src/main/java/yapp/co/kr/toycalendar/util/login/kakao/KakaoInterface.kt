package yapp.co.kr.toycalendar.util.login.kakao

import com.kakao.usermgmt.response.MeV2Response

interface KakaoInterface {
    abstract fun afterGetInfo(response: MeV2Response)
}
