package com.wsa.shows.network.api

import com.wsa.shows.network.response.SearchResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ApiHelperImpl @Inject constructor(private val apiService: ApiService) : ApiHelper {
    override fun getTrending(params: HashMap<String, String>) = flow{
        emit(apiService.getTrending(params))
    }

    override fun getSearch(params : HashMap<String, String>): Flow<SearchResponse> = flow {
        emit(apiService.getSearch(params))
    }

    override fun getSimilar(params: HashMap<String, String>) {
        TODO("Not yet implemented")
    }



}