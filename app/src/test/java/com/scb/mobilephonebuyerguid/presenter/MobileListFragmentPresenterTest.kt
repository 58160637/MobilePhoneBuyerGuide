package com.scb.mobilephonebuyerguid.presenter

import com.nhaarman.mockitokotlin2.*
import com.scb.mobilephonebuyerguid.interfaces.MobileListFragmentInterface
import com.scb.mobilephonebuyerguid.model.Mobile
import com.scb.mobilephonebuyerguid.service.MobileApiService
import mockit.Deencapsulation
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import org.mockito.ArgumentMatchers
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MobileListFragmentPresenterTest {

    @Mock
    lateinit var service: MobileApiService

    @Mock
    lateinit var testView: MobileListFragmentInterface

    @InjectMocks
    lateinit var presenter: MobileListFragmentPresenter

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
    }

    @Test
    fun unFavMobileClick() {
        //given
        val mobileList: List<Mobile> = listOf(
            getMobile(1, 2.3, isFav = true)
        )
        Deencapsulation.setField(presenter, "mobiles", mobileList)

        //when
        presenter.unFavMobileClick(mobileList[0])
        val result = Deencapsulation.getField<List<Mobile>>(presenter, "mobiles")

        //then
        assertEquals(result[0].isFav, false)
    }

    @Test
    fun favMobileClickWhenFalse() {
        //given
        val mobileList: List<Mobile> = listOf(
            getMobile(1, 2.3)
        )
        Deencapsulation.setField(presenter, "mobiles", mobileList)

        //when
        presenter.favMobileClick(mobileList[0], mock())
        val result = Deencapsulation.getField<List<Mobile>>(presenter, "mobiles")

        //then
        assertEquals(result[0].isFav, true)
    }

    @Test
    fun favMobileClickwhenTrue() {
        //given
        val mobileList: List<Mobile> = listOf(
            getMobile(1, 2.3, isFav = true)
        )
        Deencapsulation.setField(presenter, "mobiles", mobileList)

        //when
        presenter.favMobileClick(mobileList[0], mock())
        val result = Deencapsulation.getField<List<Mobile>>(presenter, "mobiles")

        //then
        assertEquals(result[0].isFav, false)
    }

    @Test
    fun setSortRating() {
        //given
        val mobileList: List<Mobile> = listOf(
            getMobile(1, 2.3),
            getMobile(2, 4.0),
            getMobile(3, 3.3)
        )
        Deencapsulation.setField(presenter, "mobiles", mobileList)

        //when
        presenter.setSortRating()
        val result = Deencapsulation.getField<List<Mobile>>(presenter, "mobiles")

        //then
        assertEquals(result[0].id, 2)
        assertEquals(result[1].id, 3)
        assertEquals(result[2].id, 1)
    }

    @Test
    fun setPricingHighToLow() {
        //given
        val mobileList: List<Mobile> = listOf(
            getMobile(1, 2.3, 120.0),
            getMobile(2, 4.0, 420.0),
            getMobile(3, 3.3, 410.0)
        )
        Deencapsulation.setField(presenter, "mobiles", mobileList)

        //when
        presenter.setPricingHighToLow()
        val result = Deencapsulation.getField<List<Mobile>>(presenter, "mobiles")

        //then
        assertEquals(result[0].id, 2)
        assertEquals(result[1].id, 3)
        assertEquals(result[2].id, 1)
    }

    @Test
    fun setPricingLowToHigh() {
        //given
        val mobileList: List<Mobile> = listOf(
            getMobile(1, 2.3, 120.0),
            getMobile(2, 4.0, 420.0),
            getMobile(3, 3.3, 410.0)
        )
        Deencapsulation.setField(presenter, "mobiles", mobileList)

        //when
        presenter.setPricingLowToHigh()
        val result = Deencapsulation.getField<List<Mobile>>(presenter, "mobiles")

        //then
        assertEquals(result[0].id, 1)
        assertEquals(result[1].id, 3)
        assertEquals(result[2].id, 2)
    }

    @Test
    fun loadMobiles() {
        //given
        whenever(service.mobiles()).thenReturn(mock())

        //when
        presenter.loadMobiles()

        //then
        verify(service).mobiles()
    }

    @Test
    fun loadMobilesApiFailed() {
        //given
        val call: Call<List<Mobile>> = mock()
        whenever(service.mobiles()).thenReturn(call)
        whenever(call.enqueue(any())).thenAnswer {
            it.getArgument<Callback<List<Mobile>>>(0).onFailure(mock(), mock())
        }

        //when
        presenter.loadMobiles()

        //then
        verify(testView).showErrorMsg(ArgumentMatchers.anyString())
        verifyNoMoreInteractions(testView)
    }

    @Test
    fun loadMobilesApiResponse() {
        //given
        val mobileList: List<Mobile> = listOf(
            getMobile(1,2.3,120.0),
            getMobile(2,4.0,420.0),
            getMobile(3,3.3,410.0)
        )
        val call: Call<List<Mobile>> = mock()
        whenever(service.mobiles()).thenReturn(call)
        whenever(call.enqueue(any())).thenAnswer {
            it.getArgument<Callback<List<Mobile>>>(0).onResponse(mock(), Response.success(mobileList))
        }

        //when
        presenter.loadMobiles()
        val result = Deencapsulation.getField<List<Mobile>>(presenter, "mobiles")

        //then
        assertEquals(result[0].id, 2)
        verify(testView).submitList(any())
    }

    @Test
    fun loadMobilesApiResponseNull() {
        //given
        val call: Call<List<Mobile>> = mock()
        whenever(service.mobiles()).thenReturn(call)
        whenever(call.enqueue(any())).thenAnswer {
            it.getArgument<Callback<List<Mobile>>>(0).onResponse(mock(), Response.success(null))
        }

        //when
        presenter.loadMobiles()

        //then
        verifyZeroInteractions(testView)
    }

    fun getMobile(id: Int = 1, rating: Double = 3.9, price: Double = 999.0, isFav: Boolean = false) =
        Mobile("Nokia", "blablabla", id, "someName", price, rating, "ulr", isFav)
}