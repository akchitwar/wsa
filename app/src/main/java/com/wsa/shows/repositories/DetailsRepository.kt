package com.wsa.shows.repositories

import android.database.sqlite.SQLiteException
import com.wsa.shows.db.WSADatabase
import com.wsa.shows.db.asDatabaseModel
import com.wsa.shows.network.api.ApiHelper
import com.wsa.shows.network.asDomainModel
import com.wsa.shows.obj.ItemModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class DetailsRepository @Inject constructor(
    private val db: WSADatabase,
    private val apiHelper: ApiHelper
) {

    private val _hasFavorite = MutableStateFlow(false)
    val hasFavorite = _hasFavorite.asStateFlow()

    private val _error = MutableStateFlow<String>("")
    val error = _error.asStateFlow()

    fun getEpisodes(seriesId: Int?, seasonId: Int?) =
        apiHelper.getEpisodes(seriesId = seriesId, seasonId = 1)
            .map {
                it.asDomainModel()
            }
            .flowOn(Dispatchers.IO)
            .catch {
                _error.emit(it.message!!)
            }


    suspend fun hasFavoriteShow(id: Int) =
        try {
            withContext(Dispatchers.IO) {
                val entity = db.getFavoriteShowsDao().getFavoriteShow(id)
                if (entity != null)
                    _hasFavorite.emit(true)
                else {
                    _hasFavorite.emit(false)
                }
            }
        } catch (ex: SQLiteException) {
            _error.emit(ex.message!!)
        } catch (ex: Exception) {
            _error.emit(ex.message!!)
        }


    suspend fun addToFavorite(itemModel: ItemModel) =
        try {
            withContext(Dispatchers.IO) {
                db.getFavoriteShowsDao().insertFavoriteShow(itemModel.asDatabaseModel())
                _hasFavorite.emit(true)
            }
        } catch (ex: SQLiteException) {
            _error.emit(ex.message!!)
        } catch (ex: Exception) {
            _error.emit(ex.message!!)
        }


    suspend fun removeFromFavorite(itemModel: ItemModel) =
        try {
            withContext(Dispatchers.IO) {
                db.getFavoriteShowsDao().deleteFavoriteShows(itemModel.id!!)
                _hasFavorite.emit(false)
            }
        } catch (ex: SQLiteException) {
            _error.emit(ex.message!!)
        } catch (ex: Exception) {
            _error.emit(ex.message!!)
        }

}




