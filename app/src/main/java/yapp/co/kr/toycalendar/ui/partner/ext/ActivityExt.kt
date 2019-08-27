package yapp.co.kr.toycalendar.ui.partner.ext

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import yapp.co.kr.toycalendar.ui.partner.PartnerActivity

// 새로운 Fragment 띄워주며 이전 스택에 이전 Fragment 저장
fun PartnerActivity.replaceFragmentInActivityInStack(fragment: Fragment, layoutResId: Int) {
    val transaction = supportFragmentManager.beginTransaction()

    transaction.replace(layoutResId, fragment)
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)

    transaction.addToBackStack(null)

    transaction.commit()
}

// 새로운 Fragment 띄워줌
fun PartnerActivity.replaceFragmentInActivity(fragment: Fragment, layoutResId: Int) {
    val transaction = supportFragmentManager.beginTransaction()

    transaction.replace(layoutResId, fragment)
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)

    // transaction.addToBackStack(null)

    transaction.commit()
}

// 백 버튼 눌렀을 시 작동하는 함수
fun PartnerActivity.backFragmentInActivity() {
    if (supportFragmentManager.backStackEntryCount != 0)
        supportFragmentManager.popBackStack()
    else
        finish()
}

