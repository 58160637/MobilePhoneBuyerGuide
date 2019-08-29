package com.scb.mobilephonebuyerguid.adapter

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.scb.mobilephonebuyerguid.MOBILE
import com.scb.mobilephonebuyerguid.fragment.MoblieListFragment
import com.scb.mobilephonebuyerguid.R
import com.scb.mobilephonebuyerguid.fragment.FavoriteListFragment

private val TAB_TITLES = arrayOf(
    R.string.tab_text_1,
    R.string.tab_text_2
)

class SectionsPagerAdapter(
    private val context: Context,
    fm: FragmentManager,
    private val listFragment : ArrayList<Fragment>
) : FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {
        var mMobileFragment = listFragment[0] as MoblieListFragment
        var mFavFragment = listFragment[1] as FavoriteListFragment
        val bundle = Bundle()
        return when(position){
            0 -> {
                mMobileFragment
            }
            else -> {
                bundle.putParcelableArrayList(MOBILE, mMobileFragment.favMobiles)
                mFavFragment.arguments = bundle
                mFavFragment
            }
        }
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return context.resources.getString(TAB_TITLES[position])
    }

    override fun getCount(): Int {
        return TAB_TITLES.size
    }
}