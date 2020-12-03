package com.tezsol.bagex.fragment

import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.tezsol.bagex.ExceptionTypeActivity
import com.tezsol.bagex.R
import com.tezsol.bagex.ScanSuccessActivity
import com.tezsol.bagex.adapter.ScanListAdapter
import com.tezsol.bagex.api.APIClient
import com.tezsol.bagex.model.request.GetOrdersReq
import com.tezsol.bagex.model.response.OrdersInfo
import com.tezsol.bagex.model.response.OrdersRes
import com.tezsol.bagex.sharedutil.SharedUtil
import com.tezsol.bagex.util.DataUtil
import com.tezsol.bagex.util.ScanDetailsUtil
import com.tezsol.bagex.util.Utilities
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.manual_awb_fragment.view.*
import kotlinx.android.synthetic.main.scan_fragment.*
import kotlinx.android.synthetic.main.scan_fragment.view.*
import kotlinx.android.synthetic.main.scan_fragment.view.scanList
import kotlinx.android.synthetic.main.scan_fragment.view.scanTtl

class ManualAWBFragment : BaseFragment() {
    private var recyclerView: RecyclerView? = null
    var scanTtlTxt: TextView? = null
    private val EXCEPTION_TYPE: Int = 0x432
    private var dialog: ProgressDialog? = null


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView: View = inflater.inflate(R.layout.manual_awb_fragment, container, false)
        scanTtlTxt = rootView.scanTtl
        rootView.awbDeleteAll.setOnClickListener(View.OnClickListener {
            ScanDetailsUtil.instance.clearOrderInfo()
            initBarcodeList()
        })
        scanTtlTxt?.text = ScanDetailsUtil.instance.getOrderInfo().size.toString()
        rootView.awbNext.setOnClickListener {
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
        recyclerView = rootView.scanList
        initBarcodeList()
        rootView.okBtn.setOnClickListener {
            val enterValue = rootView.awbInput.text.toString()
            if (TextUtils.isEmpty(enterValue)) {
                Toast.makeText(activity, "Please Enter Proper Bag ID", Toast.LENGTH_SHORT).show()
            } else
                if (!ScanDetailsUtil.instance.isContain(enterValue))
                    validateChecksum(enterValue)
                else
                    Toast.makeText(activity, "Bag Id already Scaned", Toast.LENGTH_SHORT).show()
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

    private fun validateChecksum(awbNumber: String) {
        dialog?.show()
        val delReq = GetOrdersReq(
            bagid = awbNumber
        )
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == EXCEPTION_TYPE && resultCode == Activity.RESULT_OK) {
            val exceptionType = data?.getStringExtra("exceptionType")
            updateScanStatus()
        }
    }

    private fun updateOnUI(orderInfo: OrdersInfo) {
        ScanDetailsUtil.instance.addOrderInfo(orderInfo)
        (scanList.adapter as ScanListAdapter).notifyDataSetChanged()
        scanTtlTxt?.text = ScanDetailsUtil.instance.getOrderInfo().size.toString()
        barcode_scanner?.resume()
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
                object : ScanFragment.DeleteScanListener {
                    override fun onItemDelete(ordersInfo: OrdersInfo) {
                        ScanDetailsUtil.instance.removeOrderInfo(ordersInfo)
                        (scanList.adapter as ScanListAdapter).notifyDataSetChanged()
                        scanTtlTxt?.text = ScanDetailsUtil.instance.getOrderInfo().size.toString()
                    }
                }
            )
        }
    }
}