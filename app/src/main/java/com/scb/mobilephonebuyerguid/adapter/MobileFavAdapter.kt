package com.scb.mobilephonebuyerguid.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.scb.mobilephonebuyerguid.R
import com.scb.mobilephonebuyerguid.callback.CustomItemTouchHelperListener
import com.scb.mobilephonebuyerguid.model.Mobile
import scb.academy.jinglebell.extension.setImageUrl

class MobileFavAdapter(private val listener: OnMobileFavListener) : RecyclerView.Adapter<MobileFavViewHolder>(),
    CustomItemTouchHelperListener {

    val mobile: List<Mobile>
        get() = _mobile

    private var _mobile: ArrayList<Mobile> = ArrayList<Mobile>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = MobileFavViewHolder(parent)

    override fun getItemCount(): Int {
        return if (mobile.size == 0) {
            0
        } else {
            mobile.size
        }
    }

    override fun onItemDismiss(position: Int) {
        listener.onDissmissFav(_mobile[position])
        _mobile.removeAt(position)
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: MobileFavViewHolder, position: Int) {
        holder.bind(_mobile[position])
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

    fun bind(mobile: Mobile) {
        name.text = mobile.name
        price.text = mobile.price.toString()
        rate.text = mobile.rating.toString()
        pic.setImageUrl(mobile.thumbImageURL)
    }

}

interface OnMobileFavListener {
    fun onDissmissFav(mobile: Mobile)
}
