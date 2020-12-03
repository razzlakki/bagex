package com.tezsol.bagex.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import android.widget.LinearLayout
import android.widget.RadioButton
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.tezsol.bagex.R
import com.tezsol.bagex.fragment.ScanFragment
import com.tezsol.bagex.model.response.ExceptionType
import com.tezsol.bagex.model.response.OrdersInfo
import com.tezsol.bagex.util.DataUtil
import com.tezsol.bagex.util.DateUtil
import kotlinx.android.synthetic.main.btn_item.view.*

class ExceptionListAdapter(
    private val list: List<ExceptionType>,
    private val onExceptionChangeListener: OnExceptionChangeListener
) :
    RecyclerView.Adapter<ExceptionListAdapter.ExceptionViewHolder>() {


    var selectedId: Int? = 0


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExceptionViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ExceptionViewHolder(inflater.inflate(R.layout.exception_list_item, parent, false))
    }

    override fun onBindViewHolder(holder: ExceptionViewHolder, position: Int) {
        val type: ExceptionType = list[position]
        holder.exceptionName?.text = type.name
        holder.exceptionName?.setOnClickListener{
            holder.itemView.callOnClick()
        }
        holder.exceptionName?.isChecked = (selectedId == type.id)
        holder.itemView.setOnClickListener {
            selectedId = type.id
            onExceptionChangeListener.onExceptionSelected(type)
            notifyDataSetChanged()
        }

    }

    override fun getItemCount(): Int = list.size

    fun add(ordersInfo: OrdersInfo) {
        list.plus(ordersInfo)
        notifyDataSetChanged()
    }


    class ExceptionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var exceptionName: RadioButton? = null

        init {
            exceptionName = itemView.findViewById(R.id.exceptionName)
        }


    }

    interface OnExceptionChangeListener {
        fun onExceptionSelected(exceptionType: ExceptionType)
    }

}

