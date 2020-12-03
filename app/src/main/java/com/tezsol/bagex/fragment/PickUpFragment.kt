package com.tezsol.bagex.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.tezsol.bagex.R
import com.tezsol.bagex.adapter.PickupAdapter
import com.tezsol.bagex.api.APIClient
import com.tezsol.bagex.model.request.GetOrdersReq
import com.tezsol.bagex.model.response.OrdersRes
import com.tezsol.bagex.sharedutil.SharedUtil
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.pickup_fragment.view.*

class PickUpFragment : BaseFragment() {

    var progresBar: View? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView: View = inflater.inflate(R.layout.pickup_fragment, container, false)
        rootView.pickupRecyclerView.apply {
            layoutManager = LinearLayoutManager(activity)
        }
        initData(rootView.pickupRecyclerView, rootView.noItemsText)
        progresBar = rootView.progresBar
        return rootView
    }

    private fun initData(pickupRecyclerView: RecyclerView, noItemsText: TextView) {
        val delReq = GetOrdersReq(
            statuslist = arrayOf("SA002"),
            servicetype = "SV001",
        )
        progresBar?.visibility = View.VISIBLE;
        disposable =  APIClient.create()
            .getDeliveryJobs(delReq)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { result -> checkRes(pickupRecyclerView, result, noItemsText) },
                { error -> showError(error.message, noItemsText) }
            )
    }

    private fun showError(message: String?, noItemsText: TextView) {
        noItemsText.visibility = View.VISIBLE
        progresBar?.visibility = View.GONE;
    }

    private fun checkRes(
        pickupRecyclerView: RecyclerView,
        result: OrdersRes?,
        noItemsText: TextView
    ) {
        progresBar?.visibility = View.GONE
        if (result?.data != null) {
            noItemsText.visibility = View.GONE
            pickupRecyclerView.visibility = View.VISIBLE
            pickupRecyclerView.adapter = PickupAdapter(list = result.data)
        } else {
            pickupRecyclerView.visibility = View.GONE
            noItemsText.visibility = View.VISIBLE
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        disposable?.dispose()
    }
}