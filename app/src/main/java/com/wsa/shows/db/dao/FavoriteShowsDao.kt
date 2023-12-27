package com.wsa.shows.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.wsa.shows.db.entities.FavoriteShowEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteShowsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavoriteShow(results: FavoriteShowEntity)

    @Query("select * from favorite_show_tbl")
    fun getAllFavoriteShows(): Flow<List<FavoriteShowEntity>>

    @Query("select * from favorite_show_tbl WHERE id = :id")
    fun getFavoriteShow(id: Int): FavoriteShowEntity

    @Query("DELETE FROM favorite_show_tbl WHERE id = :id")
    suspend fun deleteFavoriteShows(id: Int)

    @Query("DELETE FROM favorite_show_tbl")
    suspend fun deleteAll()


}