package com.tezsol.bagex.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.tezsol.bagex.fragment.BagImageFragment

class BagImageAdapter(
    fragmentManager: FragmentManager,
    private val count: Int,
    private val onNextClickListener: BagImageFragment.OnNextClickListener
) :
    FragmentStatePagerAdapter(fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    // 2
    override fun getItem(position: Int): Fragment {
        val instance = BagImageFragment.newInstance((position + 1), count)
        instance.setOnNextClickListener(onNextClickListener = onNextClickListener)
        return instance;
    }

    // 3
    override fun getCount(): Int {
        return count
    }
}