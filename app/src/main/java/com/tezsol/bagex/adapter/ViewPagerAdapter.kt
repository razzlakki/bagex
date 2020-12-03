package com.tezsol.bagex.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import java.util.*

class ViewPagerAdapter(fm: FragmentManager?, fragmentsList: ArrayList<Fragment>) :
    FragmentPagerAdapter(fm!!, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
    private val holder: ArrayList<Fragment> = fragmentsList
    private val count: Int = fragmentsList.size
    fun getPage(position: Int): Fragment? {
        return holder[position]
    }

    override fun getItem(position: Int): Fragment {
        return holder[position]
    }

    override fun getCount(): Int {
        return count
    }

}