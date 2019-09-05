package com.scb.mobilephonebuyerguid.presenter

import com.scb.mobilephonebuyerguid.interfaces.DetailsActivityInterface
import com.scb.mobilephonebuyerguid.model.MobilePicture
import com.scb.mobilephonebuyerguid.service.MobileApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailsActivityPresenter(private val view: DetailsActivityInterface, private val service: MobileApiService) {
    private var imagesBean: ArrayList<MobilePicture> = ArrayList<MobilePicture>()

    fun loadMobilePictures(id: Int) {
       service.pictures(id).enqueue(mobilePictureCallback)
    }

    private val mobilePictureCallback = object : Callback<List<MobilePicture>> {
        override fun onFailure(call: Call<List<MobilePicture>>, t: Throwable) {
            view.showErrorMsg(t.message.toString())
        }

        override fun onResponse(call: Call<List<MobilePicture>>, response: Response<List<MobilePicture>>) {
            if (response.isSuccessful) {
                imagesBean.clear()
                imagesBean.addAll(response.body()!!)
                val imageUrls = getArrayImageUrls(imagesBean)
                view.showPicturesSlide(imageUrls)
            }
        }
    }

    fun getArrayImageUrls(imagesBean: ArrayList<MobilePicture>): ArrayList<String> {
        val imageUrls: ArrayList<String> = ArrayList<String>()
        for (image in imagesBean) {
            if (!(image.url).contains("http", ignoreCase = true)) {
                val url = "https://" + image.url
                imageUrls.add(url)
            } else {
                imageUrls.add(image.url)
            }
        }
        return imageUrls
    }
}