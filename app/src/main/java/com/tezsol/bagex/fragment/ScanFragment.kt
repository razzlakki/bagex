package com.tezsol.bagex.fragment

import android.app.Activity.RESULT_OK
import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.zxing.ResultPoint
import com.google.zxing.client.android.BeepManager
import com.journeyapps.barcodescanner.BarcodeCallback
import com.journeyapps.barcodescanner.BarcodeResult
import com.journeyapps.barcodescanner.DecoratedBarcodeView
import com.tezsol.bagex.ExceptionTypeActivity
import com.tezsol.bagex.R
import com.tezsol.bagex.ScanSuccessActivity
import com.tezsol.bagex.adapter.ScanListAdapter
import com.tezsol.bagex.api.APIClient
import com.tezsol.bagex.model.request.GetOrdersReq
import com.tezsol.bagex.model.response.OrdersInfo
import com.tezsol.bagex.model.response.OrdersRes
import com.tezsol.bagex.util.DataUtil
import com.tezsol.bagex.util.ScanDetailsUtil
import com.tezsol.bagex.util.Utilities
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.scan_fragment.*
import kotlinx.android.synthetic.main.scan_fragment.view.*

class ScanFragment : BaseFragment() {

    private val EXCEPTION_TYPE: Int = 0x432
    private var dialog: ProgressDialog? = null
    private var recyclerView: RecyclerView? = null
    var beepManager: BeepManager? = null
    var barcode_scanner: DecoratedBarcodeView? = null
    var scanTtlTxt: TextView? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView: View = inflater.inflate(R.layout.scan_fragment, container, false)
        beepManager = BeepManager(activity)
        barcode_scanner = rootView.findViewById(R.id.barcode_scanner)
        recyclerView = rootView.scanList
        initBarcodeScanView(rootView)
        initBarcodeList()
        scanTtlTxt = rootView.scanTtl
        rootView.deleteAll.setOnClickListener(View.OnClickListener {
            ScanDetailsUtil.instance.clearOrderInfo()
            initBarcodeList()
        })
        scanTtlTxt?.text = ScanDetailsUtil.instance.getOrderInfo().size.toString()

        rootView.nextBtn.setOnClickListener {
            if (ScanDetailsUtil.instance.getOrderInfo().size > 0) {
                val statusKey: String? = arguments?.getString("scanStatus")
                if (statusKey == DataUtil.EXCEPTION) {
                    startActivityForResult(
                        Intent(activity, ExceptionTypeActivity::class.java),
                        EXCEPTION_TYPE
                    )
                } else
                    updateScanStatus()
            }
        }
        dialog = ProgressDialog(activity)
        dialog?.setMessage("Loading")
        return rootView
    }

    override fun onAllRecordsUpdated() {
        super.onAllRecordsUpdated()
        activity?.finish()
        startActivity(Intent(activity, ScanSuccessActivity::class.java))
    }


    override fun setMenuVisibility(menuVisible: Boolean) {
        super.setMenuVisibility(menuVisible)
        initBarcodeList()
    }

    private fun initBarcodeList() {
        scanTtlTxt?.text = ScanDetailsUtil.instance.getOrderInfo().size.toString()
        recyclerView?.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = ScanListAdapter(
                ScanDetailsUtil.instance.getOrderInfo(),
                object : DeleteScanListener {
                    override fun onItemDelete(ordersInfo: OrdersInfo) {
                        ScanDetailsUtil.instance.removeOrderInfo(ordersInfo)
                        (scanList.adapter as ScanListAdapter).notifyDataSetChanged()
                        scanTtlTxt?.text = ScanDetailsUtil.instance.getOrderInfo().size.toString()
                    }
                }
            )
        }

    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == EXCEPTION_TYPE && resultCode == RESULT_OK) {
            val exceptionType = data?.getStringExtra("exceptionType")
            updateScanStatus()
        }
    }


    private fun initBarcodeScanView(view: View?) {
        barcode_scanner?.initializeFromIntent(activity?.intent)
        barcode_scanner?.setStatusText("")
        //to remove red line from scanner.
        barcode_scanner?.getViewFinder()?.setVisibility(View.VISIBLE)
        barcode_scanner?.decodeContinuous(object : BarcodeCallback {
            override fun barcodeResult(result: BarcodeResult) {
                beepManager!!.playBeepSoundAndVibrate()
                barcode_scanner?.pause()
                validateChecksum(result.text)
            }

            override fun possibleResultPoints(resultPoints: List<ResultPoint>) {}
        })
    }

    private fun validateChecksum(barcode: String?) {
        if (barcode != null) {
            if (!ScanDetailsUtil.instance.isContain(barcode))
                searchForAWB(barcode)
            else
                Toast.makeText(activity, "Bag Id already Scaned", Toast.LENGTH_SHORT).show()
        }
    }

    private fun searchForAWB(awbNumber: String) {
        val delReq = GetOrdersReq(
            bagid = awbNumber
        )
        dialog?.show()
        disposable = APIClient.create()
            .getDeliveryJobs(delReq)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { result -> checkRes(result) },
                { error -> showError(error.message) }
            )
    }

    private fun showError(message: String?) {
        dialog?.dismiss()
        Utilities().showToast(activity, "Not a valid barcode")
        barcode_scanner?.resume()
    }

    private fun checkRes(result: OrdersRes?) {
        dialog?.dismiss()
        if (result?.data != null && result.data.isNotEmpty()) {
            updateOnUI(result.data[0])
        } else {
            Utilities().showToast(activity, "Not a valid barcode")
            barcode_scanner?.resume()
        }
    }

    private fun updateOnUI(orderInfo: OrdersInfo) {
        ScanDetailsUtil.instance.addOrderInfo(orderInfo)
        (scanList.adapter as ScanListAdapter).notifyDataSetChanged()
        scanTtlTxt?.text = ScanDetailsUtil.instance.getOrderInfo().size.toString()
        barcode_scanner?.resume()
    }

    override fun onPause() {
        super.onPause()
        barcode_scanner?.pause()
    }

    override fun onResume() {
        super.onResume()
        barcode_scanner?.resume()
    }

    interface DeleteScanListener {
        fun onItemDelete(ordersInfo: OrdersInfo)
    }

}

