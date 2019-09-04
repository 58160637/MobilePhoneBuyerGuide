package com.scb.mobilephonebuyerguid.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.TextView
import com.ouattararomuald.slider.ImageSlider
import com.ouattararomuald.slider.SliderAdapter
import com.ouattararomuald.slider.loaders.picasso.PicassoImageLoaderFactory
import com.scb.mobilephonebuyerguid.MOBILE
import com.scb.mobilephonebuyerguid.R
import com.scb.mobilephonebuyerguid.interfaces.DetailsActivityInterface
import com.scb.mobilephonebuyerguid.model.Mobile
import com.scb.mobilephonebuyerguid.presenter.DetailsActivityPresenter

class DetailsActivity : AppCompatActivity(), DetailsActivityInterface {

    private val presenter: DetailsActivityPresenter = DetailsActivityPresenter(this)
    private lateinit var tvDetailsPhoneName: TextView
    private lateinit var tvDetailsPhoneBrand: TextView
    private lateinit var tvDetailsPhoneDescription: TextView
    private lateinit var tvDetailsPhoneRate: TextView
    private lateinit var tvDetailsPhonePrice: TextView
    private lateinit var imageSlider: ImageSlider

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)
        init()
    }

    private fun init() {
        tvDetailsPhoneName = findViewById(R.id.detailsPhoneName)
        tvDetailsPhoneBrand = findViewById(R.id.detailsPhoneBrand)
        tvDetailsPhoneDescription = findViewById(R.id.detailsPhoneDescription)
        tvDetailsPhoneRate = findViewById(R.id.detailsPhoneRate)
        tvDetailsPhonePrice = findViewById(R.id.detailsPhonePrice)
        imageSlider = findViewById(R.id.image_slider)
        getSupportActionBar()?.setDisplayHomeAsUpEnabled(true)

        val mobiles = intent.getParcelableExtra<Mobile>(MOBILE) ?: return
        showMobileInformation(mobiles)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun showPicturesSlide(imageUrls: ArrayList<String>) {
        imageSlider.adapter = SliderAdapter(
            applicationContext,
            PicassoImageLoaderFactory(),
            imageUrls = imageUrls
        )
    }

    private fun showMobileInformation(mobiles: Mobile) {
        tvDetailsPhoneName.text = mobiles.name
        tvDetailsPhoneBrand.text = mobiles.brand
        tvDetailsPhoneDescription.text = mobiles.description
        tvDetailsPhoneRate.text = mobiles.rating.toString()
        tvDetailsPhonePrice.text = mobiles.price.toString()

        presenter.loadMobilePictures(mobiles.id)
    }

}
