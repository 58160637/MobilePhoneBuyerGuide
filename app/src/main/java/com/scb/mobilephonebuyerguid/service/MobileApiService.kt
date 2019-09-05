package com.scb.mobilephonebuyerguid.service

import com.scb.mobilephonebuyerguid.model.Mobile
import com.scb.mobilephonebuyerguid.model.MobilePicture
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface MobileApiService {

    @GET("api/mobiles/")
    fun mobiles(): Call<List<Mobile>>

    @GET("api/mobiles/{mobile_id}/images/")
    fun pictures(
        @Path("mobile_id") mobileId: Int?
    ): Call<List<MobilePicture>>


}
