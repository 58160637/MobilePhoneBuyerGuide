package com.scb.mobilephonebuyerguid.fragment


import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.scb.mobilephonebuyerguid.MOBILE
import com.scb.mobilephonebuyerguid.R
import com.scb.mobilephonebuyerguid.activity.MainActivity
import com.scb.mobilephonebuyerguid.adapter.CustomItemTouchHelperCallback
import com.scb.mobilephonebuyerguid.adapter.MobileFavAdapter
import com.scb.mobilephonebuyerguid.adapter.OnMobileFavClickListener
import com.scb.mobilephonebuyerguid.model.Mobile

class FavoriteListFragment : Fragment(), OnMobileFavClickListener {
    private lateinit var rvMobiles: RecyclerView

    private lateinit var favMobiles: ArrayList<Mobile>
    private lateinit var mobileAdapter: MobileFavAdapter
    private lateinit var touchHelperCallback : CustomItemTouchHelperCallback
    private lateinit var itemTouchHelper : ItemTouchHelper
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_favorite_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        favMobiles = arguments!!.getParcelableArrayList<Mobile>(MOBILE) as ArrayList<Mobile>

        rvMobiles = view.findViewById(R.id.recyclerMobileFavView)
        mobileAdapter = MobileFavAdapter(this)
        rvMobiles.adapter = mobileAdapter
        rvMobiles.layoutManager = LinearLayoutManager(context)
        rvMobiles.itemAnimator = DefaultItemAnimator()

        touchHelperCallback = CustomItemTouchHelperCallback(mobileAdapter)
        itemTouchHelper = ItemTouchHelper(touchHelperCallback)
        itemTouchHelper.attachToRecyclerView(rvMobiles)

        mobileAdapter.submitList(favMobiles)
    }

    fun updateListFav(list:ArrayList<Mobile>){
        favMobiles = list
        mobileAdapter.submitList(favMobiles)
    }
    override fun onMobileFavClick(mobile: Mobile) {

    }

    override fun onDissmissFav(mobile: Mobile) {
        (activity as MainActivity).mMobileFragment.unFavMobilesList(mobile)
    }

}
