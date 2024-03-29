package com.scb.mobilephonebuyerguid.service

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiManager {

    val mobilesService by lazy { createService<MobileApiService>("https://scb-test-mobile.herokuapp.com/") }

    private inline fun <reified T> createService(baseUrl: String): T =
        Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .run { create(T::class.java) }

}
