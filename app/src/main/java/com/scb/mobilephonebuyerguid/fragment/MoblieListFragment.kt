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
import com.scb.mobilephonebuyerguid.activity.DetailsActivity
import com.scb.mobilephonebuyerguid.adapter.MobileAdapter
import com.scb.mobilephonebuyerguid.adapter.OnFavClickListener
import com.scb.mobilephonebuyerguid.adapter.OnMobileClickListener
import com.scb.mobilephonebuyerguid.model.Mobile
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import android.content.res.ColorStateList
import android.graphics.Color
import android.widget.Toast
import androidx.core.widget.ImageViewCompat
import com.scb.mobilephonebuyerguid.R


class MoblieListFragment : Fragment(), OnMobileClickListener, OnFavClickListener {
    private lateinit var rvMobiles: RecyclerView
    private lateinit var mobileAdapter: MobileAdapter
    private lateinit var mobiles: List<Mobile>
    private var sortOption:Int = 0;

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(com.scb.mobilephonebuyerguid.R.layout.fragment_moblie_list, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rvMobiles = view.findViewById(com.scb.mobilephonebuyerguid.R.id.recyclerMobileView)
        mobileAdapter = MobileAdapter(this,this)
        rvMobiles.adapter = mobileAdapter
        rvMobiles.layoutManager = LinearLayoutManager(context)
        rvMobiles.itemAnimator = DefaultItemAnimator()
        loadMobiles()
    }

    private fun loadMobiles() {
        val call = ApiManager.mobilesService.mobiles().enqueue(mobilesListCallback)
    }

    private val mobilesListCallback = object : Callback<List<Mobile>> {
        override fun onFailure(call: Call<List<Mobile>>, t: Throwable) {
            Log.d("SCB_NETWORK",t.message.toString())
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
                val newArray = mobiles.sortedBy { it.price }
                mobileAdapter.submitList(newArray)
            }
            1 -> {
                sortOption = 1
                val newArray = mobiles.sortedByDescending { it.price }
                mobileAdapter.submitList(newArray)
            }
            else -> {
                sortOption = 2
                val newArray = mobiles.sortedByDescending { it.rating }
                mobileAdapter.submitList(newArray)
            }
        }
    }
    override fun onMobileClick(mobile: Mobile) {
        var intent = Intent(context, DetailsActivity::class.java)
        intent.putExtra("Mobile",mobile)
        context!!.startActivity(intent)
    }
    override fun onFavClick(mobile: Mobile,favImageView: ImageView) {
        if (mobile.isFav) {
            mobile.isFav = false
            favImageView.setImageResource(R.drawable.ic_heart)
        }
        else {
            mobile.isFav = true
            favImageView.setImageResource(R.drawable.ic_heart_bold)
        }
    }

}


