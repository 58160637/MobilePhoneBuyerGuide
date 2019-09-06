package com.scb.mobilephonebuyerguid.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.scb.mobilephonebuyerguid.MOBILE
import com.scb.mobilephonebuyerguid.activity.DetailsActivity
import com.scb.mobilephonebuyerguid.adapter.MobileAdapter
import com.scb.mobilephonebuyerguid.adapter.OnMobileClickListener
import com.scb.mobilephonebuyerguid.model.Mobile
import com.scb.mobilephonebuyerguid.R
import com.scb.mobilephonebuyerguid.activity.MainActivity
import com.scb.mobilephonebuyerguid.interfaces.MobileListFragmentInterface
import com.scb.mobilephonebuyerguid.presenter.MobileListFragmentPresenter
import com.scb.mobilephonebuyerguid.service.ApiManager
import scb.academy.jinglebell.extension.showToast

class MobileListFragment : Fragment(), OnMobileClickListener, MobileListFragmentInterface {


    private lateinit var rvMobiles: RecyclerView
    private lateinit var mobileAdapter: MobileAdapter
    private val presenter: MobileListFragmentPresenter = MobileListFragmentPresenter(this, ApiManager.mobilesService)

    companion object {
        fun newInsurance() = MobileListFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_moblie_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init(view)
    }

    private fun init(view: View) {
        rvMobiles = view.findViewById(R.id.recyclerMobileView)
        mobileAdapter = MobileAdapter(this)
        rvMobiles.adapter = mobileAdapter
        rvMobiles.layoutManager = LinearLayoutManager(context)
        rvMobiles.itemAnimator = DefaultItemAnimator()
        presenter.loadMobiles()
    }

    fun unFavMobile(mobile: Mobile) {
        presenter.unFavMobileClick(mobile)
    }

    override fun submitList(list: List<Mobile>) {
        mobileAdapter.submitList(list)
    }

    fun sortRating() {
        presenter.setSortRating()
    }

    fun sortPricingHighToLow() {
        presenter.setPricingHighToLow()
    }

    fun sortPricingLowToHigh() {
        presenter.setPricingLowToHigh()
    }

    override fun onMobileClick(mobile: Mobile) {
        var intent = Intent(context, DetailsActivity::class.java)
        intent.putExtra(MOBILE, mobile)
        context!!.startActivity(intent)
    }

    override fun setImage(favImageView: ImageView) {
        favImageView.setImageResource(R.drawable.ic_heart)
    }

    override fun setImageBold(favImageView: ImageView) {
        favImageView.setImageResource(R.drawable.ic_heart_bold)
    }

    override fun onFavClick(mobile: Mobile, favImageView: ImageView) {
        presenter.favMobileClick(mobile, favImageView)
    }

    override fun updateFavList(favList: ArrayList<Mobile>) {
        getParent()?.updateFavList(favList)
    }

    override fun showErrorMsg(errorMessage: String) {
        this.context?.showToast(errorMessage)
    }

    private fun getParent() = (activity as? MainActivity)

}


