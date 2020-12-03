package com.tezsol.bagex.fragment

import android.app.ProgressDialog
import androidx.fragment.app.Fragment
import com.tezsol.bagex.api.APIClient
import com.tezsol.bagex.model.request.UpdateScanStatusReq
import com.tezsol.bagex.model.response.UpdateStatusRes
import com.tezsol.bagex.util.DataUtil
import com.tezsol.bagex.util.ScanDetailsUtil
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

open class BaseFragment : Fragment() {


    private var dialog: ProgressDialog? = null;
    protected var disposable: Disposable? = null

    fun updateScanStatus() {
        dialog = ProgressDialog(activity);
        dialog?.setMessage("Loading")
        dialog?.show()
        var statusKey = arguments?.getString("scanStatus")
        val bdnos: MutableList<String>? = ArrayList()
        for (value in ScanDetailsUtil.instance.getOrderInfo()) {
            value.bagId?.let { bdnos?.add(it) }
        }


        val delReq = UpdateScanStatusReq(
            status = DataUtil.getInstance().getScanStatus(statusKey),
            bdnos = bdnos?.toTypedArray()
        )

        disposable = APIClient.create()
            .updateServicesStatus(delReq)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { result -> onStatusUpdated(result) },
                { error -> showError(error.message) }
            )
    }

    private fun showError(message: String?) {

    }

    private fun onStatusUpdated(result: UpdateStatusRes?) {
        onAllRecordsUpdated()
    }

    protected open fun onAllRecordsUpdated() {
        dialog?.dismiss()
    }

    override fun onDestroy() {
        super.onDestroy()
        disposable?.dispose()
    }


}