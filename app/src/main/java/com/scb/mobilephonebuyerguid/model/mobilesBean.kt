package com.scb.mobilephonebuyerguid.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


@Parcelize
data class Mobile(
    val brand: String,
    val description: String,
    val id: Int,
    val name: String,
    val price: Double,
    val rating: Double,
    val thumbImageURL: String,
    var isFav: Boolean = false
) : Parcelable


