package com.wsa.shows.repositories

import com.wsa.shows.constants.Messages.NO_DATA_FOUND
import com.wsa.shows.db.WSADatabase
import com.wsa.shows.db.asDatabaseModel
import com.wsa.shows.network.api.ApiHelper
import com.wsa.shows.network.asDomainModel
import com.wsa.shows.obj.ItemModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.toList
import javax.inject.Inject

class HomeRepository @Inject constructor(private val db: WSADatabase,
                                         private val apiHelper: ApiHelper) {

    private val _gridItemList = MutableStateFlow<List<ItemModel>>(listOf())
    val gridItemList = _gridItemList.asStateFlow()

    private val _error = MutableStateFlow("")
    val error = _error.asStateFlow()

/*
    Function will fetches all the trending shows from api
     as well insert in db and send a callback to grit view to show data
*/

    suspend fun refreshTrendingShows() = apiHelper.getTrending(hashMapOf("adult" to "true"))
        .map {
            db.getRrShowsDao().deleteAllTrShows()
            db.getRrShowsDao().insertTrShows(it.asDatabaseModel())
            _gridItemList.emit( it.asDomainModel())
        }
        .flowOn(Dispatchers.IO)
        .catch {
            _error.emit(it.message!!)
        }


    /*
    Function will do api call to search the data and send call back to grid view to populate it
*/
    suspend fun searchShows(query : String) = apiHelper.getSearch(hashMapOf("query" to query))
        .map {
            if( it.results.isNullOrEmpty()) {
                _error.emit(NO_DATA_FOUND)
                return@map
            }
            _gridItemList.emit( it.asDomainModel())
        }
        .flowOn(Dispatchers.IO)
        .catch {
            _error.emit(it.message!!)
        }

    /*
    Function will fetches all the trending shows from database send call back to grid view to populate it
*/
    suspend fun getTrShowFromDb() {
        _gridItemList.emit(db.getRrShowsDao().getTrShows().asFlow()
            .map {
                it.asDomainModel()
            }.catch {
                _error.emit(it.message!!)
            }
            .toList())
    }


    /*
  Function will check in dB data is available or not
*/
    suspend fun isTrShowsAvailableInDb() =
      db.getRrShowsDao().getTrShows().asFlow()
          .flowOn(Dispatchers.IO)
            .catch {
                _error.emit(it.message!!)
            }


    /*
 Function will fetches all the favorite shows from database
 send call back to view pager in home screen view to populate it

*/
      fun getFavoritesFromDb()=
         db.getFavoriteShowsDao().getAllFavoriteShows()
            .map { it ->
                it.map {
                    it.asDomainModel()
                }
            }
            .flowOn(Dispatchers.IO)
            .catch {
                _error.emit(it.message!!)
            }


}




