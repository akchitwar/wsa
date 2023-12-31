package com.wsa.shows.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.wsa.shows.db.DBConstants.TR_SHOW_RESULT_TBL

@Entity(tableName = TR_SHOW_RESULT_TBL)
data class TrShowEntity(

    @PrimaryKey(autoGenerate = true)
    val autoId: Int? = null,
    val id: Int? = null,
    val overview: String? = null,
    val posterPath: String? = null,
    val backdropPath: String? = null,
    val mediaType: String? = null,
    val name: String? = null

)