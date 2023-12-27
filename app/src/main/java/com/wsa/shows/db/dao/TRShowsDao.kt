package com.wsa.shows.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.wsa.shows.db.entities.TrShowEntity
@Dao
interface TRShowsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTrShows(results: List<TrShowEntity>)

    @Query("select * from tr_show_results_tbl")
    suspend fun getTrShows(): List<TrShowEntity>

    @Query("DELETE FROM tr_show_results_tbl WHERE id = :id")
    suspend fun deleteTrShows(id : Int)
    @Query("DELETE FROM tr_show_results_tbl")
    suspend fun deleteAllTrShows()
}