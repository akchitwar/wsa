package com.wsa.shows.repositories

import com.wsa.shows.db.WSADatabase
import com.wsa.shows.network.api.ApiHelperImpl
import com.wsa.shows.network.response.TrShows
import com.wsa.shows.network.response.TrendingResponse
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.mockito.Spy
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class HomeRepositoryTest{

    @Spy
    lateinit var mockDb : WSADatabase

    @Mock
    lateinit var mockApiHelper : ApiHelperImpl

    lateinit var homeRepository : HomeRepository

    private val listOFShows = listOf(TrShows(overview = "overview", name = "name"))

    @Before
    fun setUp(){
        MockitoAnnotations.openMocks(this)
        homeRepository = HomeRepository(mockDb, mockApiHelper)
    }


    @Test
    fun api_should_return_expected_data(){
        runBlocking{
            val response =  TrendingResponse(results = listOFShows)
           val flowTest = flow { emit(response) }
            Mockito.`when`(mockApiHelper.getTrending(hashMapOf("adult" to "true"))).thenReturn(flowTest)
            homeRepository.refreshTrendingShows()
            flowTest.collect{
                assertTrue(response.results?.size == it.results?.size)
                println(it)
            }

        }
    }


}