package com.tezsol.bagex

import android.view.View
import androidx.fragment.app.Fragment
import com.tezsol.bagex.adapter.ViewPagerAdapter
import com.tezsol.bagex.custom.ui.CustomTab
import com.tezsol.bagex.fragment.BeltFragment
import com.tezsol.bagex.fragment.DeliveryFragment
import com.tezsol.bagex.fragment.PickUpFragment
import kotlinx.android.synthetic.main.activity_jobs.*
import kotlinx.android.synthetic.main.app_bar_all.*
import java.util.*
import android.view.ViewTreeObserver.OnPreDrawListener as OnPreDrawListener1


class JobsActivity : DashboardItmBaseActivity() {

    private var tabNames = arrayOf("Pickup", "Delivery", "Belt")

    override fun getRootResource(): Int {
        return R.layout.activity_jobs;
    }


    override fun onPostOnCreate() {
        super.onPostOnCreate()
        supportPostponeEnterTransition();
        headerText.viewTreeObserver.addOnPreDrawListener(
            object : OnPreDrawListener1 {
                override fun onPreDraw(): Boolean {
                    headerText.viewTreeObserver.removeOnPreDrawListener(this)
                    supportStartPostponedEnterTransition()
                    return true
                }
            }
        )
        searchIcon.visibility = View.VISIBLE
        refreshIcon.visibility = View.VISIBLE
        initTabs()
        for (i in tabNames.indices) {
            tabs.getTabAt(i)?.customView = (CustomTab.getTabView(this, tabNames[i], true))
        }
    }

    private fun initTabs() {
        val list = getFragmentsList();
        val pagerAdapter =
            list?.let { ViewPagerAdapter(supportFragmentManager, it) }
        container.run {
            container.adapter = pagerAdapter
        }
        tabs.setupWithViewPager(container)
    }

    private fun getFragmentsList(): ArrayList<Fragment>? {
        val fragmentsList: ArrayList<Fragment> = ArrayList()
        fragmentsList.add(PickUpFragment())
        fragmentsList.add(DeliveryFragment())
        fragmentsList.add(BeltFragment())
        return fragmentsList
    }
}