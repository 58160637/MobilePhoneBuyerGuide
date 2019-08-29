package com.scb.mobilephonebuyerguid.activity

import android.os.Bundle
import com.google.android.material.tabs.TabLayout
import androidx.viewpager.widget.ViewPager
import androidx.appcompat.app.AppCompatActivity
import com.scb.mobilephonebuyerguid.adapter.SectionsPagerAdapter
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*
import android.content.DialogInterface
import android.view.MotionEvent
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.scb.mobilephonebuyerguid.fragment.FavoriteListFragment
import com.scb.mobilephonebuyerguid.fragment.MoblieListFragment

class MainActivity : AppCompatActivity() {

    val mMobileFragment:MoblieListFragment = MoblieListFragment()
    val mFavFragment:FavoriteListFragment = FavoriteListFragment()
    val listFragment = arrayListOf<Fragment>(mMobileFragment,mFavFragment)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.scb.mobilephonebuyerguid.R.layout.activity_main)

        val sectionsPagerAdapter = SectionsPagerAdapter(this, supportFragmentManager,listFragment)
        val viewPager: ViewPager = findViewById(com.scb.mobilephonebuyerguid.R.id.view_pager)

        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = findViewById(com.scb.mobilephonebuyerguid.R.id.tabs)
        tabs.setupWithViewPager(viewPager)

        sort.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View) {
                showDialogBox()
            }
        })
    }
    fun showDialogBox() {
        var alertDialog1: AlertDialog? = null
        val values = arrayOf<CharSequence>("Price low to high", "Price high to low", "Rating 5-1")
        val builder = AlertDialog.Builder(this@MainActivity)
        builder.setSingleChoiceItems(values, -1, DialogInterface.OnClickListener { dialog, item ->
            when (item) {
                0 ->
                    mMobileFragment.setSort(0)
                1 ->
                    mMobileFragment.setSort(1)
                2 ->
                    mMobileFragment.setSort(2)
            }
            alertDialog1?.dismiss()
        })
        alertDialog1 = builder.create()
        alertDialog1.show()
    }
}