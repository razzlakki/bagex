package com.tezsol.bagex

import android.content.Intent
import com.tezsol.bagex.util.ScanDetailsUtil
import kotlinx.android.synthetic.main.activity_scan_success.*

class ScanSuccessActivity : BaseActivity() {

    override fun onPostOnCreate() {
        ScanDetailsUtil.instance.clearOrderInfo()
        rootView.setOnClickListener {
            finishAffinity()
            startActivity(Intent(this, DashboardActivity::class.java))
        }
    }

    override fun getRootResource(): Int {
        return R.layout.activity_scan_success
    }
}