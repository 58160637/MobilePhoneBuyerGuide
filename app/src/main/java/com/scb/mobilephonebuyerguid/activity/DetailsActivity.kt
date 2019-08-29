package com.scb.mobilephonebuyerguid.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import com.ouattararomuald.slider.ImageSlider
import com.ouattararomuald.slider.SliderAdapter
import com.ouattararomuald.slider.loaders.picasso.PicassoImageLoaderFactory
import com.scb.mobilephonebuyerguid.R
import com.scb.mobilephonebuyerguid.model.Mobile
import com.scb.mobilephonebuyerguid.model.MobilePicture
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import scb.academy.jinglebell.extension.setImageUrl


//private lateinit var ivDetailsPhonePic: ImageView
private lateinit var tvDetailsPhoneName: TextView
private lateinit var tvDetailsPhoneBrand: TextView
private lateinit var tvDetailsPhoneDescription: TextView
private lateinit var tvDetailsPhoneRate: TextView
private lateinit var tvDetailsPhonePrice: TextView
private lateinit var imageSlider: ImageSlider
private var imagesBean: ArrayList<MobilePicture> = ArrayList<MobilePicture>()

class DetailsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)
        //ivDetailsPhonePic = findViewById(R.id.detailsPhonePic)
        tvDetailsPhoneName = findViewById(R.id.detailsPhoneName)
        tvDetailsPhoneBrand = findViewById(R.id.detailsPhoneBrand)
        tvDetailsPhoneDescription = findViewById(R.id.detailsPhoneDescription)
        tvDetailsPhoneRate = findViewById(R.id.detailsPhoneRate)
        tvDetailsPhonePrice = findViewById(R.id.detailsPhonePrice)

        val mobiles = intent.getParcelableExtra<Mobile>("Mobile") ?: return
        showMobileInformation(mobiles)
    }

    private val mobilePictureCallback = object : Callback<List<MobilePicture>> {
        override fun onFailure(call: Call<List<MobilePicture>>, t: Throwable) {
            Log.d("SCB_NETWORK",t.message.toString())
        }
        override fun onResponse(call: Call<List<MobilePicture>>, response: Response<List<MobilePicture>>) {
            if (response.isSuccessful){
                Log.d("SCB_NETWORK",response.body().toString())
                imagesBean.clear()
                imagesBean.addAll(response.body()!!)
                showPicturesSlide()
            }
        }
    }

    private fun showPicturesSlide() {
        val imageUrls: ArrayList<String> = ArrayList<String>()
        for (image in imagesBean){
            imageUrls.add(image.url)
        }
        imageSlider = findViewById(R.id.image_slider)
        imageSlider.adapter = SliderAdapter(
            applicationContext,
            PicassoImageLoaderFactory(),
            imageUrls = imageUrls
        )

    }

    private fun loadMobilePictures(id :Int) {
        val call = ApiManager.mobilesService.pictures(id).enqueue(mobilePictureCallback)
    }

    private fun showMobileInformation(mobiles: Mobile) {
        //ivDetailsPhonePic.setImageUrl(mobiles.thumbImageURL)
        tvDetailsPhoneName.text = mobiles.name
        tvDetailsPhoneBrand.text = mobiles.brand
        tvDetailsPhoneDescription.text = mobiles.description
        tvDetailsPhoneRate.text = mobiles.rating.toString()
        tvDetailsPhonePrice.text = mobiles.price.toString()

        loadMobilePictures(mobiles.id)
    }
}
