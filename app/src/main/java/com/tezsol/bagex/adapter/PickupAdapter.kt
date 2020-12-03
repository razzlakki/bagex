package com.tezsol.bagex.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.tezsol.bagex.R
import com.tezsol.bagex.model.response.OrdersInfo
import com.tezsol.bagex.util.DataUtil
import com.tezsol.bagex.util.DateUtil
import kotlinx.android.synthetic.main.btn_item.view.*

class PickupAdapter(private val list: List<OrdersInfo>) :
    RecyclerView.Adapter<PickupAdapter.PickupViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PickupViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return PickupViewHolder(inflater.inflate(R.layout.pickup_line_item, parent, false))
    }

    override fun onBindViewHolder(holder: PickupViewHolder, position: Int) {
        val movie: OrdersInfo = list[position]
        holder.bind(this, position, movie)

    }

    override fun getItemCount(): Int = list.size


    class PickupViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private var idName: TextView? = null
        private var name: TextView? = null
        private var addresTxt: TextView? = null
        private var phoneNumber: TextView? = null
        private var deliveryDate: TextView? = null
        private var deliveryTime: TextView? = null
        private var btnsLyt: LinearLayout? = null
        private var bottomView: LinearLayout? = null

        init {
            idName = itemView.findViewById(R.id.idName)
            name = itemView.findViewById(R.id.name)
            phoneNumber = itemView.findViewById(R.id.phoneNo)
            deliveryDate = itemView.findViewById(R.id.deliveryDate)
            deliveryTime = itemView.findViewById(R.id.deliveryTm)
            btnsLyt = itemView.findViewById(R.id.btnsLyt)
            bottomView = itemView.findViewById(R.id.bottomView)
            addresTxt = itemView.findViewById(R.id.address)
        }


        fun bind(adapter: PickupAdapter, position: Int, item: OrdersInfo) {
            idName?.text = "${item.awbNo}";
            name?.text = item.flightNo;
            phoneNumber?.text = item.tPhone
            val res: String? = item.spdDate?.replace("T", " ")?.let {
                DateUtil().getDateFromString("yyyy-MM-dd HH:MM:SS",
                    it
                )
            }?.let {
                DateUtil().getFormatDate(
                    "yyyy-MM-dd",
                    it
                )
            }
            deliveryDate?.text = res
            deliveryTime?.text = item.spdTime
            itemView.setOnClickListener {
                DataUtil.getInstance().pickupSelectedItem = position
                adapter.notifyDataSetChanged()
            }
            if (DataUtil.getInstance().pickupSelectedItem == position) {
                bottomView?.visibility = View.VISIBLE
                btnsLyt?.removeAllViews()
                addresTxt?.text = item.sLocation
                for (data in DataUtil.getInstance().pickUpListItems) {
                    val itemLayout = LayoutInflater.from(itemView.context)
                    val viewItem = itemLayout.inflate(R.layout.btn_item, null)
                    viewItem.btnName?.text = data.name
                    viewItem.btnIcon.setImageResource(data.icon)
                    if (data.name.equals("POD", true)) {
                        viewItem.btnName?.text = data.name
                        viewItem.squreBg.setBackgroundResource(R.drawable.item_btn_bg_active)
                        viewItem.btnIcon.setColorFilter(
                            ContextCompat.getColor(
                                viewItem.context,
                                R.color.white
                            ), android.graphics.PorterDuff.Mode.SRC_IN
                        );
                        viewItem.btnIcon.setImageResource(data.icon)
                    }
                    var params = viewItem?.layoutParams
                    if (params == null) {
                        params = LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.WRAP_CONTENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT,
                            1f
                        )
                    } else {
                        params = (params as LinearLayout.LayoutParams)
                        params.weight = 1f
                    }
                    viewItem.layoutParams = params
                    btnsLyt?.addView(viewItem)
                }
            } else {
                bottomView?.visibility = View.GONE
            }
        }

    }

}

