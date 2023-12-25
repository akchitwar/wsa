package com.wsa.shows.db.entities

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.wsa.shows.network.response.SearchResponse
import com.wsa.shows.network.response.TrendingResponse
import com.wsa.shows.db.DBConstants.TR_SHOW_RESULT_TBL
import com.wsa.shows.obj.ItemModel
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = TR_SHOW_RESULT_TBL)
data class TrShowEntity(

    @PrimaryKey(autoGenerate = true)
    val autoId: Int? = null,
    val id: Int? = null,
    val overview: String? = null,
    val originalLanguage: String? = null,
    val originalTitle: String? = null,
    val title: String? = null,
    val posterPath: String? = null,
    val backdropPath: String? = null,
    val mediaType: String? = null,
    val originalName: String? = null,
    val name: String? = null,
    var favorite: Boolean? = false,
    var posterPathBase64: String? = null,
    var backdropPathBase64: String? = null

) : Parcelable

/**
 * Mapping of results to trending shows entity
 */
fun TrendingResponse.asDomainModel( ): List<ItemModel> {
    return  results!!.map {
        ItemModel(
            id = it?.id,
            title = it?.title,
            originalTitle = it?.originalTitle,
            originalLanguage = it?.originalLanguage,
            originalName = it?.originalName,
            posterPath = it?.posterPath,
            backdropPath = it?.backdropPath,
            overview = it?.overview
        )
    }

}

fun SearchResponse.asDomainModel( ): List<ItemModel> {
    return  results!!.map {
        ItemModel(
            id = it?.id,
            title = it?.originalName,
            originalTitle = it?.originalName,
            originalLanguage = it?.originalLanguage,
            originalName = it?.originalName,
            posterPath = it?.posterPath,
            backdropPath = it?.backdropPath,
            overview = it?.overview
        )
    }

}