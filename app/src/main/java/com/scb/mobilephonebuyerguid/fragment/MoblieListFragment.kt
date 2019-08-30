package com.scb.mobilephonebuyerguid.fragment

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
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
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import com.scb.mobilephonebuyerguid.R
import com.scb.mobilephonebuyerguid.activity.MainActivity

class MoblieListFragment : Fragment(), OnMobileClickListener {
    private lateinit var rvMobiles: RecyclerView
    private lateinit var mobileAdapter: MobileAdapter
    private lateinit var mobiles: List<Mobile>
    var favMobiles: ArrayList<Mobile> = ArrayList<Mobile>()

    private var sortOption:Int = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(com.scb.mobilephonebuyerguid.R.layout.fragment_moblie_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rvMobiles = view.findViewById(com.scb.mobilephonebuyerguid.R.id.recyclerMobileView)
        mobileAdapter = MobileAdapter(this)
        rvMobiles.adapter = mobileAdapter
        rvMobiles.layoutManager = LinearLayoutManager(context)
        rvMobiles.itemAnimator = DefaultItemAnimator()
        loadMobiles()
    }

    fun unFavMobilesList(mobile: Mobile){
        mobiles[mobiles.indexOf(mobile)].isFav = false
        mobileAdapter.submitList(mobiles)
    }

    private fun loadMobiles() {
        val call = ApiManager.mobilesService.mobiles().enqueue(mobilesListCallback)
    }

    private val mobilesListCallback = object : Callback<List<Mobile>> {
        override fun onFailure(call: Call<List<Mobile>>, t: Throwable) {
            Log.d("SCB_NETWORK","error :"+ t.message.toString())
        }
        override fun onResponse(call: Call<List<Mobile>>, response: Response<List<Mobile>>) {
            if (response.isSuccessful){
                mobiles = response.body()!! ?: return
                setSort(sortOption)
            }
        }
    }
    fun setSort(option:Int) {
        when (option){
            0 -> {
                sortOption = 0
                mobiles = mobiles.sortedBy { it.price }
                favMobiles.sortBy { it.price }
                (activity as MainActivity).mFavFragment.updateListFav(favMobiles)
                mobileAdapter.submitList(mobiles)
            }
            1 -> {
                sortOption = 1
                mobiles = mobiles.sortedByDescending { it.price }
                favMobiles.sortByDescending { it.price }
                (activity as MainActivity).mFavFragment.updateListFav(favMobiles)
                mobileAdapter.submitList(mobiles)
            }
            else -> {
                sortOption = 2
                mobiles = mobiles.sortedByDescending { it.rating }
                favMobiles.sortByDescending { it.rating }
                (activity as MainActivity).mFavFragment.updateListFav(favMobiles)
                mobileAdapter.submitList(mobiles)
            }
        }
    }
    override fun onMobileClick(mobile: Mobile) {
        var intent = Intent(context, DetailsActivity::class.java)
        intent.putExtra(MOBILE,mobile)
        context!!.startActivity(intent)
    }
    override fun onFavClick(mobile: Mobile,favImageView: ImageView) {
        if (mobile.isFav) {
            mobile.isFav = false
            favMobiles.remove(mobile)
            favImageView.setImageResource(R.drawable.ic_heart)
            (activity as MainActivity).mFavFragment.updateListFav(favMobiles)
        }
        else {
            mobile.isFav = true
            favMobiles.add(mobile)
            favImageView.setImageResource(R.drawable.ic_heart_bold)
            (activity as MainActivity).mFavFragment.updateListFav(favMobiles)
        }
    }

}


