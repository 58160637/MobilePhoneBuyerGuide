package com.scb.mobilephonebuyerguid.presenter

import android.widget.ImageView
import com.scb.mobilephonebuyerguid.interfaces.MobileListFragmentInterface
import com.scb.mobilephonebuyerguid.model.Mobile
import com.scb.mobilephonebuyerguid.service.MobileApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MobileListFragmentPresenter(
    private val mobileListView: MobileListFragmentInterface,
    private val service: MobileApiService
) {
    private lateinit var mobiles: List<Mobile>
    var favMobiles: ArrayList<Mobile> = ArrayList<Mobile>()

    private val mobilesListCallback = object : Callback<List<Mobile>> {
        override fun onFailure(call: Call<List<Mobile>>, t: Throwable) {
            mobileListView.showErrorMsg(t.message.toString())
        }

        override fun onResponse(call: Call<List<Mobile>>, response: Response<List<Mobile>>) {
            if (response.isSuccessful) {
                response.body()?.apply {
                    mobiles =  this
                    setSortRating()
                }
            }
        }
    }

    fun unFavMobileClick(mobile: Mobile) {
        mobiles[mobiles.indexOf(mobile)].isFav = false
        mobileListView.submitList(mobiles)
    }

    fun favMobileClick(mobile: Mobile, favImageView: ImageView) {
        if (mobile.isFav) {
            mobile.isFav = false
            favMobiles.remove(mobile)
            mobileListView.setImage(favImageView)
            mobileListView.updateFavList(favMobiles)
        } else {
            mobile.isFav = true
            favMobiles.add(mobile)
            mobileListView.setImageBold(favImageView)
            mobileListView.updateFavList(favMobiles)
        }

    }

    fun setSortRating() {
        mobiles = mobiles.sortedByDescending { it.rating }
        mobileListView.submitList(mobiles)
    }

    fun setPricingHighToLow() {
        mobiles = mobiles.sortedByDescending { it.price }
        mobileListView.submitList(mobiles)
    }

    fun setPricingLowToHigh() {
        mobiles = mobiles.sortedBy { it.price }
        mobileListView.submitList(mobiles)
    }

    fun loadMobiles() {
        service.mobiles().enqueue(mobilesListCallback)
    }


}