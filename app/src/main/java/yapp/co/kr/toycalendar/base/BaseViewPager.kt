package buv.co.kr.base

import android.os.Parcelable
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.google.android.material.tabs.TabLayout

abstract class BaseViewPager {
    class Adapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {
        private val fragmentList = mutableListOf<BaseFragment>()
        private val fragmentTitles = mutableListOf<String>()

        fun addFragmentList(list: Array<BaseFragment>) {
            fragmentList.addAll(list)
        }

        fun addTitleList(array: Array<String>) {
            fragmentTitles.addAll(array)
        }

        override fun getItem(position: Int): Fragment {
            return fragmentList[position]
        }

        override fun getPageTitle(position: Int): CharSequence {
            return fragmentTitles[position]
        }

        override fun getCount(): Int {
            return fragmentList.size
        }

        // fragmentManager 가 id를 찾지 못하는 증상이 발생하여 넣음
        override fun saveState(): Parcelable? {
            return null
        }
    }
}

// var viewPagerAdapter = BaseViewPager.Adapter(supportFragmentManager)
// // 1. 프레그먼트 add
// viewPagerAdapter.addFragmentList(arrayOf(DefaultFragment.newInstance(), DefaultFragment.newInstance()))
// // 2. ViewPager Title add
// viewPagerAdapter.addTitleList(arrayOf("전체", "팔로잉"))
// // 3. setAdapter
// vp_category.adapter = viewPagerAdapter
//
// // 4. 탭 레이아웃 연동이 필요한 경우 설정 (tl_category : 탭레이아웃)
// tl_category.setupWithViewPager(vp_category)
// tl_category.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
//     override fun onTabSelected(tab: TabLayout.Tab) {
//         vp_category.currentItem = tab.position
//         mainVM.currentItem.set(tab.position)
//     }
//
//     override fun onTabUnselected(tab: TabLayout.Tab) {
//
//     }
//
//     override fun onTabReselected(tab: TabLayout.Tab) {
//
//     }
// })