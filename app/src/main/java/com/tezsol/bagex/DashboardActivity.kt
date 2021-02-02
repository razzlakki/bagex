package com.tezsol.bagex

import android.content.Intent
import android.view.View
import androidx.core.view.GravityCompat
import androidx.recyclerview.widget.GridLayoutManager
import com.tezsol.bagex.adapter.DashboardAdapter
import com.tezsol.bagex.animutil.AnimateUtil
import com.tezsol.bagex.sharedutil.SharedUtil
import kotlinx.android.synthetic.main.activity_dasboard.*
import kotlinx.android.synthetic.main.app_bar.*
import kotlinx.android.synthetic.main.app_bar_all.*

class DashboardActivity : BaseActivity() {


    override fun getRootResource(): Int {
        return R.layout.activity_dasboard;
    }

    override fun onPostOnCreate() {
        initAnims()
        initRecycleView()
        bagexTextView.setOnClickListener {
            drawerLayout.openDrawer(GravityCompat.END)
        }
        logoutLayout.setOnClickListener {
            SharedUtil.instance.putBoolean(SharedUtil.IS_LOGIN, false)
            startActivity(Intent(this, SplashActivity::class.java))
            finishAffinity()
        }
    }


    private fun initRecycleView() {
        dashboardRecyclerView.apply {
            layoutManager = GridLayoutManager(this@DashboardActivity, 2)
            adapter = DashboardAdapter(getItems(), this@DashboardActivity)
        }

    }

    private fun getItems(): List<DashboardAdapter.DashboardItems> {
        val items = ArrayList<DashboardAdapter.DashboardItems>()
        items.add(
            DashboardAdapter.DashboardItems(
                "PICK UP",
                R.drawable.ic_jobs,
                R.id.action_job,
                PickupActivity::class.java
            )
        )
        items.add(
            DashboardAdapter.DashboardItems(
                "DEIVERY", R.drawable.ic_delivery, 0,
                DeliveryActivity::class.java
            )
        )
        items.add(
            DashboardAdapter.DashboardItems(
                "BELT",
                R.drawable.ic_exceptions,
                R.id.action_exception,
                BeltActivity::class.java
            )
        )
        items.add(
            DashboardAdapter.DashboardItems(
                "OPERATIONS SCAN", R.drawable.ic_scan, 0,
                TracingActivity::class.java
            )
        )
        items.add(
            DashboardAdapter.DashboardItems(
                "TRACKING", R.drawable.ic_tracking, 0,
                InboundActivity::class.java
            )
        )
        items.add(
            DashboardAdapter.DashboardItems(
                "SCANS", R.drawable.ic_outbound, 0,
                OutBoundActivity::class.java
            )
        )
//        items.add(DashboardAdapter.DashboardItems("PROFILE", R.drawable.ic_profile))
        return items;
    }

    private fun initAnims() {
        AnimateUtil.instance.animateFromLeft(headerLogo, AnimateUtil.AnimFrom.TOP)
        AnimateUtil.instance.animateFromLeft(notificationIcon, AnimateUtil.AnimFrom.LEFT)
        AnimateUtil.instance.animateFromLeft(bagexTextView, AnimateUtil.AnimFrom.BOTTOM)
        AnimateUtil.instance.animateFromLeft(welcomeText, AnimateUtil.AnimFrom.BOTTOM)
    }
}