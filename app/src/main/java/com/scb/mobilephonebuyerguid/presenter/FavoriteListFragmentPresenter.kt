package com.scb.mobilephonebuyerguid.presenter

import com.scb.mobilephonebuyerguid.interfaces.FavoriteListFragmentInterface
import com.scb.mobilephonebuyerguid.model.Mobile

class FavoriteListFragmentPresenter(private val fragment: FavoriteListFragmentInterface) {

    private lateinit var favMobiles: ArrayList<Mobile>

    fun init() {
        favMobiles = ArrayList<Mobile>()
    }

    fun sortRating() {
        favMobiles.sortByDescending { it.rating }
        fragment.submitList(favMobiles)
    }

    fun sortPricingHighToLow() {
        favMobiles.sortByDescending { it.price }
        fragment.submitList(favMobiles)
    }

    fun sortPricingLowToHigh() {
        favMobiles.sortBy { it.price }
        fragment.submitList(favMobiles)
    }

    fun updateFavList(list: ArrayList<Mobile>) {
        favMobiles = list
    }

}