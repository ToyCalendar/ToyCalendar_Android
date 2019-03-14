package buv.co.kr.base

import android.app.Application
import androidx.databinding.ObservableField
import androidx.lifecycle.AndroidViewModel

open class BaseViewModel(app: Application) : AndroidViewModel(app) {
    // 이벤트 리스너 관련 변수
    var action: SingleLiveEvent<String> = SingleLiveEvent()
    var actionField: ObservableField<String> = ObservableField()

    // 컴포넌트가 클릭 상태인지 확인
    protected var clicked: Boolean = false

    // 이벤트 리스너 처리
    open fun callAction(action: String) {
        // TODO 토의 내용2_1. 클릭 중복 방지를 이렇게 처리해주는 게 옳은 걸까?
        // 중복 클릭 방지
        if (!clicked) {
            freezeClick()
            this.action.value = action
            actionField.set(action)
        }
    }

    fun releaseClick() {
        clicked = false
    }

    fun freezeClick() {
        clicked = true
    }
}