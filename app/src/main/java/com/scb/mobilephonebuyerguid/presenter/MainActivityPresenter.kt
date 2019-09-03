package com.scb.mobilephonebuyerguid.presenter

import com.scb.mobilephonebuyerguid.interfaces.MainActivityInterface

class MainActivityPresenter(private val view: MainActivityInterface){

    companion object {
        const val PRICE_LOW_TO_HIGH = 0
        const val PRICE_HIGH_TO_LOW = 1
        const val RATING = 2
    }

    var sortOption:Int = 0
    fun sortItem(item: Int) {
        when (item) {
            RATING ->{
                sortOption = RATING
                view.sortRating()
            }
            PRICE_HIGH_TO_LOW ->{
                sortOption = PRICE_HIGH_TO_LOW
                view.sortPricingHighToLow()
            }
            PRICE_LOW_TO_HIGH ->{
                sortOption = PRICE_LOW_TO_HIGH
                view.sortPricingLowToHigh()
            }
        }
    }

}