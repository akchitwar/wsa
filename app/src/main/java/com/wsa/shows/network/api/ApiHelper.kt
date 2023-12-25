package com.wsa.shows.network.api

import com.wsa.shows.network.response.SearchResponse
import com.wsa.shows.network.response.TrendingResponse
import kotlinx.coroutines.flow.Flow

interface ApiHelper {
    fun getTrending(map: HashMap<String, String>) : Flow<TrendingResponse>

    fun getSearch(map: HashMap<String, String>) : Flow<SearchResponse>

    fun getSimilar(map: HashMap<String, String>)



}