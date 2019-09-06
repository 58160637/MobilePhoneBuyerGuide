package com.scb.mobilephonebuyerguid.presenter

import com.nhaarman.mockitokotlin2.*
import com.scb.mobilephonebuyerguid.interfaces.DetailsActivityInterface
import com.scb.mobilephonebuyerguid.model.MobilePicture
import com.scb.mobilephonebuyerguid.service.MobileApiService
import org.junit.Before
import org.junit.Test
import org.junit.Assert.*
import org.mockito.ArgumentMatchers
import org.mockito.ArgumentMatchers.anyInt
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class DetailsActivityPresenterTest {

    lateinit var presenter: DetailsActivityPresenter

    @Mock
    lateinit var service : MobileApiService

    @Mock
    lateinit var testView: DetailsActivityInterface

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        presenter = DetailsActivityPresenter(testView, service)
    }

    @Test
    fun loadMobilePictures() {
        //given
        whenever(service.pictures(any())).thenReturn(mock())

        //when
        presenter.loadMobilePictures(1)

        //then
        verify(service).pictures(anyInt())
    }

    @Test
    fun loadMobilePicturesApiFailed() {
        //given
        val call: Call<List<MobilePicture>> = mock()
        whenever(service.pictures(any())).thenReturn(call)
        whenever(call.enqueue(any())).thenAnswer {
            it.getArgument<Callback<List<MobilePicture>>>(0).onFailure(mock(), mock())
        }

        //when
        presenter.loadMobilePictures(1)

        //then
        verify(testView).showErrorMsg(ArgumentMatchers.anyString())
        verifyNoMoreInteractions(testView)
    }

    @Test
    fun loadMobilePicturesApiResponseBodyEmptyList() {
        //given
        val call = mock<Call<List<MobilePicture>>>()
        whenever(service.pictures(any())).thenReturn(call)
        whenever(call.enqueue(any())).thenAnswer {
            it.getArgument<Callback<List<MobilePicture>>>(0).onResponse(mock(), Response.success(listOf()))
        }

        //when
        presenter.loadMobilePictures(1)

        //then
        verify(testView).showPicturesSlide(any())
    }

    @Test
    fun loadMobilePicturesApiBodyMobilePictureModel() {
        //given
        val beerModel = MobilePicture(0,0,"")
        val call = mock<Call<List<MobilePicture>>>()
        whenever(service.pictures(any())).thenReturn(call)
        whenever(call.enqueue(any())).thenAnswer {
            it.getArgument<Callback<List<MobilePicture>>>(0).onResponse(mock(), Response.success(listOf(beerModel)))
        }

        //when
        presenter.loadMobilePictures(1)

        //then
        verify(testView).showPicturesSlide(any())
    }

    @Test
    fun getArrayImageUrls() {
        //given
        val mocks = MobilePicture(0,0,"")
        val imagesBean = arrayListOf<MobilePicture>(mocks)

        //when
        val imaUrls = presenter.getArrayImageUrls(imagesBean)

        //then
        assertEquals(imaUrls.size,imagesBean.size)
    }

    @Test
    fun getArrayImageUrlsWithNoHTTP() {
        //given
        val mocks = MobilePicture(0,0,"www.bla.com")
        val imagesBean = arrayListOf<MobilePicture>(mocks)

        //when
        val imaUrls = presenter.getArrayImageUrls(imagesBean)

        //then
        assert(imaUrls[0].startsWith("https://"))
    }

    @Test
    fun getArrayImageUrlsWithHTTP() {
        //given
        val mocks = MobilePicture(0,0,"http://www.bla.com")
        val imagesBean = arrayListOf<MobilePicture>(mocks)

        //when
        val imaUrls = presenter.getArrayImageUrls(imagesBean)

        //then
        assertEquals(imaUrls[0],imagesBean[0].url)
    }
}