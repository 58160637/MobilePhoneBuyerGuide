package com.scb.mobilephonebuyerguid.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Mobile(
    @SerializedName("brand")
    val brand: String,

    @SerializedName("description")
    val description: String,

    @SerializedName("id")
    val id: Int,

    @SerializedName("name")
    val name: String,

    @SerializedName("price")
    val price: Double,

    @SerializedName("rating")
    val rating: Double,

    @SerializedName("thumbImageURL")
    val thumbImageURL: String,

    @SerializedName("isFav")
    var isFav: Boolean = false

) : Parcelable


