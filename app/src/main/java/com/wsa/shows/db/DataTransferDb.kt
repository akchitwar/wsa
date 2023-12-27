package com.wsa.shows.db

import com.wsa.shows.db.entities.FavoriteShowEntity
import com.wsa.shows.db.entities.TrShowEntity
import com.wsa.shows.network.response.TrendingResponse
import com.wsa.shows.obj.ItemModel

/*Mapping data to database entity*/
fun TrendingResponse.asDatabaseModel(): List<TrShowEntity> {
    return results!!.map {
        TrShowEntity(
            id = it?.id,
            name = it?.name,
            posterPath = it?.posterPath,
            backdropPath = it?.backdropPath,
            mediaType = it?.mediaType,
            overview = it?.overview

        )
    }
}

fun ItemModel.asDatabaseModel(): FavoriteShowEntity {

    return FavoriteShowEntity(
        id = this.id,
        name = this.name,
        posterPath = this.posterPath,
        backdropPath = this.backdropPath,
        overview = this.overview,

        )

}