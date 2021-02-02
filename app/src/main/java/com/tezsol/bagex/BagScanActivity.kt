package com.tezsol.bagex

import android.widget.Toast
import androidx.viewpager.widget.ViewPager.OnPageChangeListener
import com.tezsol.bagex.adapter.BagImageAdapter
import com.tezsol.bagex.fragment.BagImageFragment
import kotlinx.android.synthetic.main.activity_cam_scan.*
import kotlinx.android.synthetic.main.app_bar_all.*
import java.util.*

class BagScanActivity : BaseActivity() {
    private var awbNo: Long? = null
    private var pickBags: Int? = null
    private var noBags: Int? = null
    private val mItems = ArrayList<BagTagInfo>()

    override fun getRootResource(): Int {
        return R.layout.activity_cam_scan
    }

    override fun onPostOnCreate() {
        initValues()
        initViewPager()
        headerText?.text = "Bag 1 of $pickBags"
    }

    private fun initViewPager() {
        val pagerAdapter = pickBags?.let {
            BagImageAdapter(
                supportFragmentManager,
                it,
                object : BagImageFragment.OnNextClickListener {
                    override fun onNextClick(pos: Int, bagTagInfo: BagTagInfo) {
                        mItems
                        if ((pos + 1) >= pickBags!!) {
                            submitToNext()
                        } else {
                            viewPager.currentItem = pos.inc()
                        }

                    }

                })
        }
        viewPager.adapter = pagerAdapter
        viewPager.addOnPageChangeListener(object : OnPageChangeListener {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {

            }

            override fun onPageSelected(position: Int) {
                headerText?.text = "Bag ${(position + 1)} of $pickBags"
            }

            override fun onPageScrollStateChanged(state: Int) {
            }

        })
    }

    private fun submitToNext() {
        Toast.makeText(this, "Completed", Toast.LENGTH_LONG).show()
    }

    private fun initValues() {
        noBags = intent.getIntExtra("noBags", 0);
        pickBags = intent.getIntExtra("pickBags", 0);
        awbNo = intent.getLongExtra("awbNo", 0);
    }

    class BagTagInfo {
        var pos: Int? = null
        var tagNo: String? = null
        var sealNo: String? = null
    }

}