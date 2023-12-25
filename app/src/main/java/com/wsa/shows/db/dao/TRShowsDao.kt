package com.wsa.shows.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.wsa.shows.db.entities.TrShowEntity

@Dao
interface TRShowsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertResults(results: List<TrShowEntity>)

    @Query("select * from tr_show_results_tbl")
    fun getTrendShows(): List<TrShowEntity>


}