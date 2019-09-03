package com.scb.mobilephonebuyerguid.interfaces

import android.widget.ImageView
import com.scb.mobilephonebuyerguid.model.Mobile

interface MoblieListFragmentInterface {
    fun submitList(list: List<Mobile>)
    fun setImage(favImageView: ImageView)
    fun setImageBold(favImageView: ImageView)
    fun updateFavList(favList: ArrayList<Mobile>)
}
