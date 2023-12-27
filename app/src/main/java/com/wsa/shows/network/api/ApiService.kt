package com.wsa.shows.network.api

import com.wsa.shows.network.response.EpisodesResponse
import com.wsa.shows.network.response.SearchResponse
import com.wsa.shows.network.response.TrendingResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.QueryMap

interface ApiService {

    @GET("/3/trending/tv/week")
    suspend fun getTrending(@QueryMap params : Map<String, String>) : TrendingResponse

    @GET("/3/search/tv")
    suspend fun getSearch(@QueryMap params : Map<String, String>) : SearchResponse

    @GET("/3/tv/{series_id}/season/{season_id}")
    suspend fun getEpisodes(@Path("series_id")seriesId : Int?,
                            @Path("season_id")seasonId : Int?): EpisodesResponse

    @GET("/tv/196511/similar?language=en-US&page=1")
    suspend fun getSimilar(@QueryMap params : Map<String, String>)


}