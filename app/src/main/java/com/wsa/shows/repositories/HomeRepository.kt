package com.wsa.shows.repositories

import com.wsa.shows.network.asDatabaseModel
import com.wsa.shows.network.api.ApiHelper
import com.wsa.shows.db.dao.TRShowsDao
import com.wsa.shows.db.entities.asDomainModel
import com.wsa.shows.obj.ItemModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class HomeRepository @Inject constructor(private val dao: TRShowsDao,
                                         private val apiHelper: ApiHelper) {

    private val _gridItemList = MutableStateFlow<List<ItemModel>>(listOf())
    val gridItemList = _gridItemList.asStateFlow()

    suspend fun refreshTrendingShows() = apiHelper.getTrending(hashMapOf("adult" to "true"))
        .map {
            dao.insertResults(it.asDatabaseModel())
            _gridItemList.emit( it.asDomainModel())
        }
        .flowOn(Dispatchers.IO)


    suspend fun searchShows(query : String) = apiHelper.getSearch(hashMapOf("query" to query))
        .map {
          //  dao.insertResults(it.asDatabaseModel())
            _gridItemList.emit( it.asDomainModel())
        }
        .flowOn(Dispatchers.IO)
}




