package org.triniti.greensmart.ui.home.shop.purchase

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class FragmentAdapter(fm: FragmentManager) :
    FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
    private val fragments: MutableList<Fragment> = mutableListOf()

    override fun getItem(position: Int): Fragment {
        return fragments[position]
    }

    override fun getCount() = fragments.size

    fun addFragment(fragment: Fragment) {
        fragments.add(fragment)
    }
}