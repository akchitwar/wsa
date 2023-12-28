package com.wsa.shows.repositories

import com.wsa.shows.db.WSADatabase
import com.wsa.shows.db.dao.TRShowsDao
import com.wsa.shows.network.api.ApiHelperImpl
import com.wsa.shows.network.response.TrShows
import com.wsa.shows.network.response.TrendingResponse
import kotlinx.coroutines.flow.collect
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
class HomeRepositoryTest {

    @Spy
    lateinit var mockDb: WSADatabase

    @Mock
    lateinit var mockApiHelper: ApiHelperImpl

    @Mock
    lateinit var trShowsDao: TRShowsDao

    lateinit var homeRepository: HomeRepository

    private val listOFShows = listOf(TrShows(overview = "overview", name = "name"))

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        Mockito.`when`(mockDb.getRrShowsDao()).thenReturn(trShowsDao)

        homeRepository = HomeRepository(mockDb, mockApiHelper)
    }


    @Test
    fun when_fetch_trending_shows_Should_delete_all_data_from_trending_shows_table() {
        runBlocking {
            val response = TrendingResponse(results = listOFShows)
            val responseFlow = flow { emit(response) }
            Mockito.`when`(mockApiHelper.getTrending(hashMapOf("adult" to "true")))
                .thenReturn(responseFlow)
            Mockito.`when`(mockDb.getRrShowsDao()).thenReturn(trShowsDao)
            homeRepository.refreshTrendingShows().collect()
            Mockito.verify(mockDb.getRrShowsDao(), Mockito.atLeastOnce()).deleteAllTrShows()

        }
    }

    @Test
    fun when_fetch_trending_shows_Should_insert_all_data_in_trending_shows_table() {
        runBlocking {
            val response = TrendingResponse(results = listOFShows)
            val responseFlow = flow { emit(response) }
            Mockito.`when`(mockApiHelper.getTrending(hashMapOf("adult" to "true")))
                .thenReturn(responseFlow)
            homeRepository.refreshTrendingShows().collect()
            Mockito.verify(mockDb.getRrShowsDao(), Mockito.atLeastOnce())
                .insertTrShows(Mockito.anyList())

        }
    }

    @Test(expected = java.lang.NullPointerException::class)
    fun when_fetch_trending_shows_but_expected_data_are_missing_should_throw_exception() {
        runBlocking {
            val response = TrendingResponse()
            val responseFlow = flow { emit(response) }
            Mockito.`when`(mockApiHelper.getTrending(hashMapOf("adult" to "true")))
                .thenReturn(responseFlow)
            homeRepository.refreshTrendingShows().collect()
            Mockito.doThrow(NullPointerException())
        }
    }

}