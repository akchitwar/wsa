package com.wsa.shows.network.api

import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ApiHelperImpl @Inject constructor(private val apiService: ApiService) : ApiHelper {
    override fun getTrending(params: HashMap<String, String>) = flow{
        emit(apiService.getTrending(params))
    }

    override fun getSearch(params : HashMap<String, String>) = flow {
        emit(apiService.getSearch(params))
    }

    override fun getSimilar(params: HashMap<String, String>) {
        TODO("Not yet implemented")
    }

    override fun getEpisodes(seriesId: Int?, seasonId: Int?) = flow  {
        emit(apiService.getEpisodes(seriesId, seasonId))
    }


}