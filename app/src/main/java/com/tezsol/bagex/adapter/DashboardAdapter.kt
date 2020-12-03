package com.tezsol.bagex.adapter

import android.app.ActivityOptions
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.tezsol.bagex.BaseActivity
import com.tezsol.bagex.JobsActivity
import com.tezsol.bagex.R
import com.tezsol.bagex.animutil.AnimateUtil

class DashboardAdapter(private val list: List<DashboardItems>, private val activity: BaseActivity) :
    RecyclerView.Adapter<DashboardAdapter.DashboardViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DashboardViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return DashboardViewHolder(inflater.inflate(R.layout.dashboard_grid_item, parent, false))
    }

    override fun onBindViewHolder(holder: DashboardViewHolder, position: Int) {
        val movie: DashboardItems = list[position]
        holder.bind(position, movie, activity)
    }

    override fun getItemCount(): Int = list.size


    class DashboardViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private var itemName: TextView? = null
        private var dashIcon: ImageView? = null

        init {
            itemName = itemView.findViewById(R.id.itemName)
            dashIcon = itemView.findViewById(R.id.dashIcon);
        }


        fun bind(position: Int, item: DashboardItems, activity: BaseActivity) {
            AnimateUtil.instance.animateFromLeft(
                itemView,
                if (position % 2 == 0) AnimateUtil.AnimFrom.LEFT else AnimateUtil.AnimFrom.RIGHT,
                (position + 1) * 200L
            )

            itemName?.text = item.name
            dashIcon?.setImageResource(item.icon)

            itemView.setOnClickListener {
                val intent = Intent(activity, item.destination)
                intent.putExtra("selectedItem", item.action)
                val options = ActivityOptions
                    .makeSceneTransitionAnimation(activity, itemName, "robot")
                // start the new activity
                activity.startActivity(intent, options.toBundle())
            }
        }

    }


    class DashboardItems(
        val name: String,
        val icon: Int,
        val action: Int,
        val destination: Class<*>
    ) {
    }

}
