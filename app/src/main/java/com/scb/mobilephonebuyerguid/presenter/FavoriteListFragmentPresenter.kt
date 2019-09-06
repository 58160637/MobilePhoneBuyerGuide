package com.scb.mobilephonebuyerguid.presenter

import com.scb.mobilephonebuyerguid.interfaces.FavoriteListFragmentInterface
import com.scb.mobilephonebuyerguid.model.Mobile

class FavoriteListFragmentPresenter(private val favoriteListView: FavoriteListFragmentInterface) {

    private lateinit var favMobiles: ArrayList<Mobile>

    fun init() {
        favMobiles = ArrayList<Mobile>()
    }

    fun sortRating() {
        favMobiles.sortByDescending { it.rating }
        favoriteListView.submitList(favMobiles)
    }

    fun sortPricingHighToLow() {
        favMobiles.sortByDescending { it.price }
        favoriteListView.submitList(favMobiles)
    }

    fun sortPricingLowToHigh() {
        favMobiles.sortBy { it.price }
        favoriteListView.submitList(favMobiles)
    }

    fun updateFavList(list: ArrayList<Mobile>) {
        favMobiles = list
    }

}