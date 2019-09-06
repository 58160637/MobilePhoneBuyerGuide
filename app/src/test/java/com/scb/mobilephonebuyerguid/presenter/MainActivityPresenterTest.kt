package com.scb.mobilephonebuyerguid.presenter

import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.verifyZeroInteractions
import com.scb.mobilephonebuyerguid.interfaces.MainActivityInterface
import org.junit.Before
import org.junit.Test

import org.junit.Assert.*
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class MainActivityPresenterTest {

    @Mock
    lateinit var testView: MainActivityInterface

    @InjectMocks
    lateinit var presenter: MainActivityPresenter

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
    }

    @Test
    fun sortItemRATING() {
        //when
        presenter.sortItem(RATING)
        //then
        verify(testView).sortRating()
    }

    @Test
    fun sortItemPRICE_LOW_TO_HIGH() {
        //when
        presenter.sortItem(PRICE_LOW_TO_HIGH)
        //then
        verify(testView).sortPricingLowToHigh()
    }

    @Test
    fun sortItemPRICE_HIGH_TO_LOW() {
        //when
        presenter.sortItem(PRICE_HIGH_TO_LOW)
        //then
        verify(testView).sortPricingHighToLow()
    }

    @Test
    fun sortItemNon() {
        //when
        presenter.sortItem(9)
        //then
        verifyZeroInteractions(testView)
    }

    @Test
    fun setSortOption() {
        //when
        presenter.setSortOption()

        //then
        verify(testView).setSelectSortOption(any())
    }

    companion object {
        const val PRICE_LOW_TO_HIGH = 0
        const val PRICE_HIGH_TO_LOW = 1
        const val RATING = 2
    }
}