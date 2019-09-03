package com.scb.mobilephonebuyerguid.activity

import android.os.Bundle
import com.google.android.material.tabs.TabLayout
import androidx.viewpager.widget.ViewPager
import androidx.appcompat.app.AppCompatActivity
import com.scb.mobilephonebuyerguid.adapter.SectionsPagerAdapter
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*
import android.content.DialogInterface
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.scb.mobilephonebuyerguid.fragment.FavoriteListFragment
import com.scb.mobilephonebuyerguid.fragment.MoblieListFragment
import com.scb.mobilephonebuyerguid.interfaces.MainActivityInterface
import com.scb.mobilephonebuyerguid.model.Mobile
import com.scb.mobilephonebuyerguid.presenter.MainActivityPresenter

class MainActivity : AppCompatActivity(), MainActivityInterface {

    companion object {
        const val PRICE_LOW_TO_HIGH = "Price low to high"
        const val PRICE_HIGH_TO_LOW = "Price high to low"
        const val RATING_5_1 = "Rating 5-1"
    }

    private val presenter: MainActivityPresenter = MainActivityPresenter(this)

    lateinit var mMobileFragment: MoblieListFragment
    lateinit var mFavFragment: FavoriteListFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.scb.mobilephonebuyerguid.R.layout.activity_main)
        setPager()

    }

    fun showDialogBox() {
        var sortDialog: AlertDialog? = null
        val values = arrayOf<CharSequence>(PRICE_LOW_TO_HIGH, PRICE_HIGH_TO_LOW, RATING_5_1)
        val builder = AlertDialog.Builder(this@MainActivity)
        builder.setSingleChoiceItems(values, -1, DialogInterface.OnClickListener { _, item ->
            presenter.sortItem(item)
            sortDialog?.dismiss()
        })
        sortDialog = builder.create()
        sortDialog.show()
    }

    fun setPager() {
        mMobileFragment = MoblieListFragment.newInsurance()
        mFavFragment = FavoriteListFragment.newInsurance()
        val listFragment = arrayListOf<Fragment>(mMobileFragment,mFavFragment)

        val tabs: TabLayout = findViewById(com.scb.mobilephonebuyerguid.R.id.tabs)
        val viewPager: ViewPager = findViewById(com.scb.mobilephonebuyerguid.R.id.view_pager)

        val sectionsPagerAdapter = SectionsPagerAdapter(this, supportFragmentManager, listFragment)
        viewPager.adapter = sectionsPagerAdapter
        tabs.setupWithViewPager(viewPager)

        sort.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View) {
                showDialogBox()
            }
        })
    }

    override fun sortRating() {
        mMobileFragment.sortRating()
        mFavFragment.sortRating()
    }

    override fun sortPricingHighToLow() {
        mMobileFragment.sortPricingHighToLow()
        mFavFragment.sortPricingHighToLow()
    }

    override fun sortPricingLowToHigh() {
        mMobileFragment.sortPricingLowToHigh()
        mFavFragment.sortPricingLowToHigh()
    }
    fun unFavMobile(mobile: Mobile){
        mMobileFragment.unFavMobile(mobile)
    }

    fun updateFavList(favList: ArrayList<Mobile>) {
        mFavFragment.updateFavList(favList)
    }
}
