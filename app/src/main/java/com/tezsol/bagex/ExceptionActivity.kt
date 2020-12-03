package com.tezsol.bagex

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ViewTreeObserver
import android.view.ViewTreeObserver.OnPreDrawListener
import androidx.fragment.app.Fragment
import com.tezsol.bagex.adapter.ViewPagerAdapter
import com.tezsol.bagex.custom.ui.CustomTab
import com.tezsol.bagex.fragment.BaseFragment
import com.tezsol.bagex.fragment.ManualAWBFragment
import com.tezsol.bagex.fragment.ScanFragment
import com.tezsol.bagex.util.DataUtil
import kotlinx.android.synthetic.main.activity_jobs.*
import kotlinx.android.synthetic.main.app_bar_all.*
import java.util.ArrayList

class ExceptionActivity : DashboardItmBaseActivity() {

    override fun onPostOnCreate() {
        super.onPostOnCreate()
        headerText.text = "Exception"
        supportPostponeEnterTransition();
        headerText.viewTreeObserver.addOnPreDrawListener(
            object : OnPreDrawListener {
                override fun onPreDraw(): Boolean {
                    headerText.viewTreeObserver.removeOnPreDrawListener(this)
                    supportStartPostponedEnterTransition()
                    return true
                }
            }
        )
        initTabs()
    }

    override fun getRootResource(): Int {
        return R.layout.activity_exception
    }


    private var tabNames = arrayOf("Scan AWB", "Enter Seal")


    private fun initTabs() {
        val list = getFragmentsList()
        val pagerAdapter =
            list?.let { ViewPagerAdapter(supportFragmentManager, it) }
        container.run {
            container.adapter = pagerAdapter
        }
        tabs.setupWithViewPager(container)
        for (i in tabNames.indices) {
            tabs.getTabAt(i)?.customView = (CustomTab.getTabView(this, tabNames[i], false))
        }
    }

    private fun getFragmentsList(): ArrayList<Fragment>? {
        val fragmentsList: ArrayList<Fragment> = ArrayList()
        fragmentsList.add(getFragment(ScanFragment()))
        fragmentsList.add(getFragment(ManualAWBFragment()))
        return fragmentsList
    }

    private fun getFragment(fragment: BaseFragment): Fragment {
        val arguments = Bundle()
        arguments.putString("scanStatus", DataUtil.EXCEPTION)
        fragment.arguments = arguments
        return fragment
    }
}