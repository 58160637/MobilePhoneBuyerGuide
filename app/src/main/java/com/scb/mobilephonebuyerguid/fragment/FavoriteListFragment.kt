package com.scb.mobilephonebuyerguid.fragment


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.scb.mobilephonebuyerguid.R
import com.scb.mobilephonebuyerguid.activity.MainActivity
import com.scb.mobilephonebuyerguid.adapter.MobileFavAdapter
import com.scb.mobilephonebuyerguid.adapter.OnMobileFavListener
import com.scb.mobilephonebuyerguid.callback.CustomItemTouchHelperCallback
import com.scb.mobilephonebuyerguid.interfaces.FavoriteListFragmentInterface
import com.scb.mobilephonebuyerguid.model.Mobile
import com.scb.mobilephonebuyerguid.presenter.FavoriteListFragmentPresenter

class FavoriteListFragment : Fragment(), OnMobileFavListener, FavoriteListFragmentInterface {
    private lateinit var rvMobiles: RecyclerView
    private lateinit var mobileAdapter: MobileFavAdapter
    private lateinit var touchHelperCallback: CustomItemTouchHelperCallback
    private lateinit var itemTouchHelper: ItemTouchHelper
    private val presenter: FavoriteListFragmentPresenter = FavoriteListFragmentPresenter(this)

    companion object {
        fun newInsurance() = FavoriteListFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_favorite_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init(view)
        presenter.init()
    }

    private fun init(view: View) {
        rvMobiles = view.findViewById(R.id.recyclerMobileFavView)
        mobileAdapter = MobileFavAdapter(this)
        rvMobiles.adapter = mobileAdapter
        rvMobiles.layoutManager = LinearLayoutManager(context)
        rvMobiles.itemAnimator = DefaultItemAnimator()
        touchHelperCallback = CustomItemTouchHelperCallback(mobileAdapter)
        itemTouchHelper = ItemTouchHelper(touchHelperCallback)
        itemTouchHelper.attachToRecyclerView(rvMobiles)
    }

    override fun submitList(list: ArrayList<Mobile>) {
        mobileAdapter.submitList(list)
    }

    override fun updateFavList(list: ArrayList<Mobile>) {
        presenter.updateFavList(list)
        submitList(list)
    }

    override fun sortRating() {
        presenter.sortRating()
    }

    override fun sortPricingHighToLow() {
        presenter.sortPricingHighToLow()
    }

    override fun sortPricingLowToHigh() {
        presenter.sortPricingLowToHigh()
    }

    override fun onDissmissFav(mobile: Mobile) {
        getParent()?.unFavMobile(mobile)
    }

    private fun getParent() = (activity as? MainActivity)

}
