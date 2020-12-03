package com.tezsol.bagex

import android.content.Intent
import com.tezsol.bagex.util.DataUtil
import com.tezsol.bagex.util.DateUtil
import kotlinx.android.synthetic.main.activity_scan_options.*
import kotlinx.android.synthetic.main.app_bar_all.*

class ScanOptionActivity : BaseActivity() {

    override fun getRootResource(): Int {
        return R.layout.activity_scan_options
    }

    override fun onPostOnCreate() {
        headerText.text = "Scan Types"
        initClicks()
    }

    private fun initClicks() {
        ofdLyt.setOnClickListener {
            val scanIntent = Intent(this, ScanActivity::class.java)
            scanIntent.putExtra("scanStatus", DataUtil.OFD)
            scanIntent.putExtra("headerName", "OFD")
            startActivity(scanIntent)
        }
        podLyt.setOnClickListener {
            val scanIntent = Intent(this, ScanActivity::class.java)
            scanIntent.putExtra("scanStatus", DataUtil.POD)
            scanIntent.putExtra("headerName", "POD")
            startActivity(scanIntent)
        }
        massPodLyt.setOnClickListener {
            val scanIntent = Intent(this, ScanActivity::class.java)
            scanIntent.putExtra("scanStatus", DataUtil.POD)
            scanIntent.putExtra("headerName", "Mass POD")
            startActivity(scanIntent)
        }
        rtsLyt.setOnClickListener {
            val scanIntent = Intent(this, ScanActivity::class.java)
            scanIntent.putExtra("scanStatus", DataUtil.RTS)
            scanIntent.putExtra("headerName", "RTS")
            startActivity(scanIntent)
        }
    }
}