package com.scb.mobilephonebuyerguid.presenter

import android.util.Log
import android.widget.ImageView
import com.scb.mobilephonebuyerguid.interfaces.MobileListFragmentInterface
import com.scb.mobilephonebuyerguid.model.Mobile
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MobileListFragmentPresenter(private val fragment: MobileListFragmentInterface) {
    private lateinit var mobiles: List<Mobile>
    var favMobiles: ArrayList<Mobile> = ArrayList<Mobile>()

    private val mobilesListCallback = object : Callback<List<Mobile>> {
        override fun onFailure(call: Call<List<Mobile>>, t: Throwable) {
            Log.d("SCB_NETWORK", "error :" + t.message.toString())
        }

        override fun onResponse(call: Call<List<Mobile>>, response: Response<List<Mobile>>) {
            if (response.isSuccessful) {
                mobiles = response.body()!!
                setSortRating()
            }
        }
    }

    fun unFavMobileClick(mobile: Mobile) {
        mobiles[mobiles.indexOf(mobile)].isFav = false
        fragment.submitList(mobiles)
    }

    fun favMobileClick(mobile: Mobile, favImageView: ImageView) {
        if (mobile.isFav) {
            mobile.isFav = false
            favMobiles.remove(mobile)
            fragment.setImage(favImageView)
            fragment.updateFavList(favMobiles)
        } else {
            mobile.isFav = true
            favMobiles.add(mobile)
            fragment.setImageBold(favImageView)
            fragment.updateFavList(favMobiles)
        }

    }

    fun setSortRating() {
        mobiles = mobiles.sortedByDescending { it.rating }
        fragment.submitList(mobiles)
    }

    fun setPricingHighToLow() {
        mobiles = mobiles.sortedByDescending { it.price }
        fragment.submitList(mobiles)
    }

    fun setPricingLowToHigh() {
        mobiles = mobiles.sortedBy { it.price }
        fragment.submitList(mobiles)
    }

    private fun loadMobiles(mobilesListCallback: Callback<List<Mobile>>) {
        ApiManager.mobilesService.mobiles().enqueue(mobilesListCallback)
    }

    fun init() {
        loadMobiles(mobilesListCallback)
    }


}