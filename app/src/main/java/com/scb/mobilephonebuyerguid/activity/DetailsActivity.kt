package com.scb.mobilephonebuyerguid.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.ImageView
import android.widget.TextView
import com.ouattararomuald.slider.ImageSlider
import com.ouattararomuald.slider.SliderAdapter
import com.ouattararomuald.slider.loaders.picasso.PicassoImageLoaderFactory
import com.scb.mobilephonebuyerguid.MOBILE
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

        val mobiles = intent.getParcelableExtra<Mobile>(MOBILE) ?: return
        showMobileInformation(mobiles)

        getSupportActionBar()?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }

    private val mobilePictureCallback = object : Callback<List<MobilePicture>> {
        override fun onFailure(call: Call<List<MobilePicture>>, t: Throwable) {
            Log.d("SCB_NETWORK",t.message.toString())
        }
        override fun onResponse(call: Call<List<MobilePicture>>, response: Response<List<MobilePicture>>) {
            if (response.isSuccessful){
                imagesBean.clear()
                imagesBean.addAll(response.body()!!)
                showPicturesSlide()
            }
        }
    }

    private fun showPicturesSlide() {
        val imageUrls: ArrayList<String> = ArrayList<String>()
        for (image in imagesBean){
            if(!(image.url).contains("http",ignoreCase = true)){
                var url = "https://"+image.url
                imageUrls.add(url)
            }else{
                imageUrls.add(image.url)
            }
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
