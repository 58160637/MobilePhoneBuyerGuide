package com.scb.mobilephonebuyerguid.interfaces

import com.scb.mobilephonebuyerguid.model.Mobile

interface FavoriteListFragmentInterface {
    fun sortRating()
    fun sortPricingHighToLow()
    fun sortPricingLowToHigh()
    fun updateFavList(list: ArrayList<Mobile>)
    fun submitList(list: ArrayList<Mobile>)
}