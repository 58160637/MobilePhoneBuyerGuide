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
import android.widget.TextView
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.scb.mobilephonebuyerguid.R
import com.scb.mobilephonebuyerguid.activity.DetailsActivity
import com.scb.mobilephonebuyerguid.adapter.MobileAdapter
import com.scb.mobilephonebuyerguid.adapter.OnMobileClickListener
import com.scb.mobilephonebuyerguid.model.Mobile
import kotlinx.android.synthetic.main.custom_list.view.*
import kotlinx.android.synthetic.main.fragment_moblie_list.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MoblieListFragment : Fragment(), OnMobileClickListener {
    private lateinit var rvMobiles: RecyclerView
    private lateinit var mobileAdapter: MobileAdapter
    //private var mDataArray: ArrayList<Mobile> = ArrayList<Mobile>()
    //private lateinit var mAdapter: CustomAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
//        val _view = inflater.inflate(R.layout.fragment_moblie_list, container, false)
//        mAdapter = CustomAdapter(context!!)
//        _view.recyclerView.let {
//            it.adapter = mAdapter
//            it.layoutManager = LinearLayoutManager(activity)
//        }
//        return _view
        return inflater.inflate(R.layout.fragment_moblie_list, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rvMobiles = view.findViewById(R.id.recyclerMobileView)
        mobileAdapter = MobileAdapter(this)
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
//                mDataArray.clear()
//                mDataArray.addAll(response.body()!!)
//                mAdapter.notifyDataSetChanged()
                val mobiles = response.body()!! ?: return
                mobileAdapter.submitList(mobiles)
            }
        }
    }

//    inner class CustomAdapter(val context: Context) :
//        RecyclerView.Adapter<CustomHolder>() {
//        override fun onBindViewHolder(holder: CustomHolder, position: Int) {
//            val item = mDataArray[position]
//            holder.name.text = item.name
//            holder.description.text = item.description
//            holder.price.text = item.price.toString()
//            holder.rate.text = item.rating.toString()
//            Glide.with(context).load(item.thumbImageURL).into(holder.pic)
//            holder.pic.setTag(R.id.phonePic,item.name)
//        }
//        override fun getItemCount(): Int = mDataArray.size
//        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomHolder {
//            return CustomHolder(
//                LayoutInflater.from(parent.context).inflate(
//                    R.layout.custom_list,
//                    parent,
//                    false
//                )
//            )
//        }
//    }
//
//    inner class CustomHolder(view: View) : RecyclerView.ViewHolder(view) {
//        val name: TextView = view.phoneName
//        val description: TextView = view.phoneDescription
//        val pic: ImageView = view.phonePic
//        val price: TextView = view.phonePrice
//        val rate: TextView = view.phoneRating
//    }

    override fun onMobileClick(mobile: Mobile) {
        var intent = Intent(context, DetailsActivity::class.java)
        intent.putExtra("Mobile",mobile)
        context!!.startActivity(intent)
    }

}


