package com.wsa.shows.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.wsa.shows.db.DBConstants.FAVORITE_SHOW_TBL

@Entity(tableName = FAVORITE_SHOW_TBL)
data class FavoriteShowEntity(

    @PrimaryKey
    val id: Int? = null,
    val overview: String? = null,
    val posterPath: String? = null,
    val backdropPath: String? = null,
    val mediaType: String? = null,
    val name: String? = null,
)