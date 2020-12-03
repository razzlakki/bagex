package com.tezsol.bagex.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.tezsol.bagex.R
import com.tezsol.bagex.fragment.ScanFragment
import com.tezsol.bagex.model.response.OrdersInfo
import com.tezsol.bagex.util.DataUtil
import com.tezsol.bagex.util.DateUtil
import kotlinx.android.synthetic.main.btn_item.view.*

class ScanListAdapter(
    private val list: List<OrdersInfo>,
    val listener: ScanFragment.DeleteScanListener
) :
    RecyclerView.Adapter<ScanListAdapter.PickupViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PickupViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return PickupViewHolder(inflater.inflate(R.layout.scan_list_item, parent, false))
    }

    override fun onBindViewHolder(holder: PickupViewHolder, position: Int) {
        val movie: OrdersInfo = list[position]
        holder.bind(this, position, movie)

    }

    override fun getItemCount(): Int = list.size

    fun add(ordersInfo: OrdersInfo) {
        list.plus(ordersInfo)
        notifyDataSetChanged()
    }


    class PickupViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private var bagId: TextView? = null
        private var awbNo: TextView? = null
        private var deleteLyt: View? = null

        init {
            bagId = itemView.findViewById(R.id.bagId)
            awbNo = itemView.findViewById(R.id.awbNo)
            deleteLyt = itemView.findViewById(R.id.deleteLyt)
        }


        fun bind(adapter: ScanListAdapter, position: Int, item: OrdersInfo) {
            awbNo?.text = "${item.awbNo}"
            bagId?.text = item.bagId
            deleteLyt?.setOnClickListener { adapter.listener.onItemDelete(item) }
        }

    }

}

