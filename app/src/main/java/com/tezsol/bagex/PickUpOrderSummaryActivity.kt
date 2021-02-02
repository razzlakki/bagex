package com.tezsol.bagex

import android.content.Intent
import android.view.View
import android.widget.TextView
import com.tezsol.bagex.api.APIClient
import com.tezsol.bagex.model.request.PackagePricingReq
import com.tezsol.bagex.model.response.OrdersInfo
import com.tezsol.bagex.model.response.OrdersRes
import com.tezsol.bagex.model.response.PackagePriceRes
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_pick_up_order_summury.*
import kotlinx.android.synthetic.main.app_bar_all.*

class PickUpOrderSummaryActivity : BaseActivity() {
    private var noBags: Int? = 0;
    private var pickBags: Int? = 0;
    private var awbNo: Long = 0
    override fun getRootResource(): Int {
        return R.layout.activity_pick_up_order_summury
    }

    override fun onPostOnCreate() {
        headerText?.text = "Order Summary"
        initViews()
    }

    private fun initViews() {
        awbNo = intent.getLongExtra("awbNumber", 0L)
        findViewById<View>(R.id.startScan).setOnClickListener {
            val scanIntent: Intent = Intent(this, BagScanActivity::class.java)
            scanIntent.putExtra("awbNo", awbNo)
            scanIntent.putExtra("noBags", noBags)
            scanIntent.putExtra("pickBags", pickBags)
            startActivity(scanIntent)
        }
        if (awbNo != 0L) {
            progresBar?.visibility = View.VISIBLE;
            disposable = APIClient.create()
                .getOrderByAWBNo(awbNo)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { result -> checkRes(result = result) },
                    { error -> showError(error.message) }
                )
        }
    }

    private fun showError(message: String?) {
        priceProgressBar?.visibility = View.GONE
    }


    private fun checkRes(result: OrdersRes?) {
        progresBar?.visibility = View.GONE
        if (result?.data != null && result.data.isNotEmpty()) {
            updateOnUI(result.data[0])
        }
    }

    private fun updateOnUI(ordersInfo: OrdersInfo) {
        this.noBags = ordersInfo.bagsNo
        this.pickBags = noBags;
        order_no?.text = "${ordersInfo.awbNo}"
        terminal_no?.text = ordersInfo.transcId
        destinationTime?.text = ordersInfo.flightTime
        location_time?.text = ordersInfo.spdTime
        pickupTime?.text = ordersInfo.tpupdt
        pickupDate?.text = ordersInfo.spdDate
        mobileNumber?.text = ordersInfo.tPhone
        bagCount?.text = "${ordersInfo.bagsNo} Bags"
        count?.text = "$pickBags"
        ordersInfo.tStatus
        userName?.text = ordersInfo.firstName
        additionalAmount?.text = "SAR ${ordersInfo.tCharges}"
        plus?.setOnClickListener {
            pickBags = pickBags?.plus(1)
            updateBagsCountUI(ordersInfo)
        }
        minus?.setOnClickListener {
            pickBags = pickBags?.minus(1)
            updateBagsCountUI(ordersInfo)
        }
        updateBagsCountUI(ordersInfo)
    }

    private fun updateBagsCountUI(ordersInfo: OrdersInfo) {
        count?.text = "$pickBags"
        if (noBags!! < pickBags!!) {
            priceProgressBar?.visibility = View.VISIBLE
            additionalAmount?.visibility = View.GONE
            val packPrice: PackagePricingReq = PackagePricingReq()
            packPrice.code = ordersInfo.tService
            packPrice.status = "ACTIVE"
            disposable = APIClient.create()
                .getPackagePricing(packPrice)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { result -> updateActualBagsPrice(result) },
                    { error -> showError(error.message) }
                )

        } else {
            additionalAmount?.text = "SAR 0"
        }

    }

    private fun updateActualBagsPrice(result: PackagePriceRes?) {
        additionalAmount?.visibility = View.VISIBLE
        priceProgressBar?.visibility = View.GONE
        val bagValue: Int? = (result?.data?.get(0)?.bag ?: 0)
        val aditionalCount: Int? = (noBags?.let {
            pickBags?.minus(it)
        })
        var totalValue = bagValue?.times(aditionalCount!!)
        additionalAmount?.text = "SAR $totalValue"
    }


}