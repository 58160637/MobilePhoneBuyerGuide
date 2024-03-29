package com.scb.mobilephonebuyerguid.adapter

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.scb.mobilephonebuyerguid.R

private val TAB_TITLES = arrayOf(
    R.string.tab_text_1,
    R.string.tab_text_2
)

class SectionsPagerAdapter(
    private val context: Context,
    fm: FragmentManager,
    private val listFragment: ArrayList<Fragment>
) : FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {
        return listFragment[position]
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return context.resources.getString(TAB_TITLES[position])
    }

    override fun getCount(): Int {
        return listFragment.size
    }
}