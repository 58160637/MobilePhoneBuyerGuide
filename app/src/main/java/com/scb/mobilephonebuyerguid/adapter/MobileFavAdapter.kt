package com.scb.mobilephonebuyerguid.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.scb.mobilephonebuyerguid.R
import com.scb.mobilephonebuyerguid.model.Mobile
import scb.academy.jinglebell.extension.setImageUrl

class MobileFavAdapter(private val listener: OnMobileFavClickListener)
    : RecyclerView.Adapter<MobileFavViewHolder>() , CustomItemTouchHelperListener{
    val mobile: List<Mobile>
        get() = _mobile

    private var _mobile: ArrayList<Mobile> = ArrayList<Mobile>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)= MobileFavViewHolder (parent)

    override fun getItemCount(): Int {
        return if (mobile.count() == 0) {
            0
        } else {
            mobile.count()
        }
    }

    override fun onItemDismiss(position: Int) {
        _mobile.removeAt(position)
        notifyItemRemoved(position)
    }
    
    override fun onBindViewHolder(holder: MobileFavViewHolder, position: Int) {
        holder.bind(_mobile[position], listener)
    }

    fun submitList(list: ArrayList<Mobile>) {
        _mobile = list
        notifyDataSetChanged()
    }
}
class MobileFavViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
    LayoutInflater.from(parent.context).inflate(R.layout.custom_fav_list, parent, false)
) {

    private val name: TextView = itemView.findViewById(R.id.phoneName)
    private val pic: ImageView = itemView.findViewById(R.id.phonePic)
    private val price: TextView = itemView.findViewById(R.id.phonePrice)
    private val rate: TextView = itemView.findViewById(R.id.phoneRating)

    fun bind(mobile: Mobile, listener: OnMobileFavClickListener) {
        name.text = mobile.name
        price.text = mobile.price.toString()
        rate.text = mobile.rating.toString()
        pic.setImageUrl(mobile.thumbImageURL)

        itemView.setOnClickListener {
            listener.onMobileFavClick(mobile)
        }
    }
}

interface OnMobileFavClickListener {
    fun onMobileFavClick(mobile: Mobile)
}

class CustomItemTouchHelperCallback(private var listener: CustomItemTouchHelperListener) : ItemTouchHelper.Callback() {
    override fun getMovementFlags(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder): Int {
        val dragFlags = 0
        val swipeFlags = ItemTouchHelper.START or ItemTouchHelper.END
        return ItemTouchHelper.Callback.makeMovementFlags(dragFlags, swipeFlags)
    }
    override fun isLongPressDragEnabled(): Boolean {
        return false
    }

    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        return false
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        viewHolder?.let {
            listener.onItemDismiss(viewHolder.adapterPosition)
        }
    }
}

interface CustomItemTouchHelperListener {
    fun onItemDismiss(position: Int)
}


