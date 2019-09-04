package com.scb.mobilephonebuyerguid.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.scb.mobilephonebuyerguid.R
import com.scb.mobilephonebuyerguid.model.Mobile
import scb.academy.jinglebell.extension.setImageUrl

class MobileAdapter(private val listener: OnMobileClickListener) : RecyclerView.Adapter<MobileItemViewHolder>() {

    val mobile: List<Mobile>
        get() = _mobile

    private var _mobile: List<Mobile> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = MobileItemViewHolder(parent)

    override fun onBindViewHolder(holder: MobileItemViewHolder, position: Int) {
        holder.bind(_mobile[position], listener)
    }

    override fun getItemCount(): Int {
        return if (mobile.size == 0) {
            0
        } else {
            mobile.size
        }
    }

    fun submitList(list: List<Mobile>) {
        _mobile = list
        notifyDataSetChanged()
    }

}

class MobileItemViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
    LayoutInflater.from(parent.context).inflate(R.layout.custom_list, parent, false)
) {

    private val name: TextView = itemView.findViewById(R.id.phoneName)
    private val description: TextView = itemView.findViewById(R.id.phoneDescription)
    private val pic: ImageView = itemView.findViewById(R.id.phonePic)
    private val price: TextView = itemView.findViewById(R.id.phonePrice)
    private val rate: TextView = itemView.findViewById(R.id.phoneRating)
    private val fav: ImageView = itemView.findViewById(R.id.favButton)

    fun bind(mobile: Mobile, listener: OnMobileClickListener) {
        name.text = mobile.name
        description.text = mobile.description
        price.text = mobile.price.toString()
        rate.text = mobile.rating.toString()
        pic.setImageUrl(mobile.thumbImageURL)

        if (mobile.isFav)
            fav.setImageResource(R.drawable.ic_heart_bold)
        else
            fav.setImageResource(R.drawable.ic_heart)

        fav.setOnClickListener {
            listener.onFavClick(mobile, fav)
        }
        itemView.setOnClickListener {
            listener.onMobileClick(mobile)
        }
    }

}

interface OnMobileClickListener {
    fun onMobileClick(mobile: Mobile)
    fun onFavClick(mobile: Mobile, favImageView: ImageView)
}
