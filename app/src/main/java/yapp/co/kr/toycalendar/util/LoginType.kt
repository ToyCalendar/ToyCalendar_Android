package yapp.co.kr.toycalendar.util

import yapp.co.kr.toycalendar.R

// enum : 행동을 강제하고 싶을 때 사용
// ex> LOGIN_TYPE
// ex> SharedPreference const 사용하지 않고, 바로 enum을 활용할 수 있다.
// 장점
// 1. 텍스트는 오타가 나거나 다른 타입이 추가되었을 때 알기 어렵고 직접 다 고쳐주어야 함
// 2. Enum의 경우 LoginType 에 데이터만 추가시키면 됨
// 3. when문의 경우 else 예외처리를 하지 않아도됨 (Sealed class 느낌)
// 4. 제약을 거는 느낌

// GUEST : GUEST MODE
enum class LoginType {
    GOOGLE {
        override val backgroundResourceId = R.drawable.btn_login_button_google
        override val logoResourceId = R.drawable.btn_login_button_google
    },
    FACEBOOK {
        override val backgroundResourceId = R.drawable.btn_login_button_facebook
        override val logoResourceId = R.drawable.btn_login_button_facebook
    },
    KAKAO {
        override val backgroundResourceId = R.drawable.btn_login_button_kakao
        override val logoResourceId = R.drawable.btn_login_button_kakao
    },
    DEFAULT {
        override val backgroundResourceId = R.drawable.btn_login_button_default
        override val logoResourceId = 0
    },
    GUEST {
        override val backgroundResourceId = 0
        override val logoResourceId = 0
    };

    abstract val backgroundResourceId: Int
    abstract val logoResourceId: Int
}
