package com.scb.mobilephonebuyerguid.presenter

import com.scb.mobilephonebuyerguid.interfaces.FavoriteListFragmentInterface
import com.scb.mobilephonebuyerguid.model.Mobile
import mockit.Deencapsulation
import org.junit.Before
import org.junit.Test

import org.junit.Assert.*
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class FavoriteListFragmentPresenterTest {


    @Mock
    lateinit var testView: FavoriteListFragmentInterface

    @InjectMocks
    lateinit var presenter: FavoriteListFragmentPresenter

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
    }

    @Test
    fun init() {
        //give
        Deencapsulation.setField(presenter, "favMobiles", ArrayList<Mobile>())

        //when
        presenter.init()
        val result = Deencapsulation.getField<List<Mobile>>(presenter, "favMobiles")

        //then
        assert(result.isEmpty())
    }

    @Test
    fun sortRating() {
        //given
        val mobileList: ArrayList<Mobile> = arrayListOf(
            getMobile(1, 2.3),
            getMobile(2, 4.0),
            getMobile(3, 3.3)
        )
        Deencapsulation.setField(presenter, "favMobiles", mobileList)

        //when
        presenter.sortRating()
        val result = Deencapsulation.getField<ArrayList<Mobile>>(presenter, "favMobiles")

        //then
        assertEquals(result[0].id, 2)
        assertEquals(result[1].id, 3)
        assertEquals(result[2].id, 1)
    }

    @Test
    fun sortPricingHighToLow() {
        //given
        val mobileList: ArrayList<Mobile> = arrayListOf(
            getMobile(1, 2.3, 120.0),
            getMobile(2, 4.0, 420.0),
            getMobile(3, 3.3, 410.0)
        )
        Deencapsulation.setField(presenter, "favMobiles", mobileList)

        //when
        presenter.sortPricingHighToLow()
        val result = Deencapsulation.getField<ArrayList<Mobile>>(presenter, "favMobiles")

        //then
        assertEquals(result[0].id, 2)
        assertEquals(result[1].id, 3)
        assertEquals(result[2].id, 1)
    }

    @Test
    fun sortPricingLowToHigh() {
        //given
        val mobileList: ArrayList<Mobile> = arrayListOf(
            getMobile(1, 2.3, 120.0),
            getMobile(2, 4.0, 420.0),
            getMobile(3, 3.3, 410.0)
        )
        Deencapsulation.setField(presenter, "favMobiles", mobileList)

        //when
        presenter.sortPricingLowToHigh()
        val result = Deencapsulation.getField<ArrayList<Mobile>>(presenter, "favMobiles")

        //then
        assertEquals(result[0].id, 1)
        assertEquals(result[1].id, 3)
        assertEquals(result[2].id, 2)
    }

    @Test
    fun updateFavList() {
        //given
        val oldMobileList: ArrayList<Mobile> = arrayListOf(getMobile(1))
        Deencapsulation.setField(presenter, "favMobiles", oldMobileList)
        val newMobileList: ArrayList<Mobile> = arrayListOf(getMobile(2))

        //when
        presenter.updateFavList(newMobileList)
        val result = Deencapsulation.getField<ArrayList<Mobile>>(presenter, "favMobiles")

        //then
        assertEquals(result[0].id, 2)
    }

    fun getMobile(id: Int = 1, rating: Double = 3.9, price: Double = 999.0, isFav: Boolean = false) =
        Mobile("Nokia", "blablabla", id, "someName", price, rating, "ulr", isFav)
}