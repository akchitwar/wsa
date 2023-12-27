package com.wsa.shows.network

import com.wsa.shows.db.entities.FavoriteShowEntity
import com.wsa.shows.db.entities.TrShowEntity
import com.wsa.shows.network.response.EpisodesResponse
import com.wsa.shows.network.response.TrendingResponse
import com.wsa.shows.network.response.SearchResponse
import com.wsa.shows.obj.EpisodeModel
import com.wsa.shows.obj.ItemModel

/*Mapping data to model*/

fun TrendingResponse.asDomainModel(): List<ItemModel> {
    return results!!.map {
        ItemModel(
            id = it?.id,
            title = it?.title,
            name = it?.name,
            posterPath = it?.posterPath,
            backdropPath = it?.backdropPath,
            overview = it?.overview
        )
    }

}

fun SearchResponse.asDomainModel(): List<ItemModel> {
    return results!!.map {
        ItemModel(
            id = it?.id,
            title = it?.originalName,
            posterPath = it?.posterPath,
            backdropPath = it?.backdropPath,
            overview = it?.overview
        )
    }

}

fun EpisodesResponse.asDomainModel(): List<EpisodeModel> {
    return episodes!!.map {
        EpisodeModel(
            id = it?.id,
            episodeNumber = it?.episodeNumber,
            seasonNumber = it?.seasonNumber,
            name = it?.name,
            overview = it?.overview,
            stillPath = it?.stillPath
        )
    }

}

fun FavoriteShowEntity.asDomainModel(): ItemModel {
    return ItemModel(
        id = this.id,
        overview = this.overview,
        name = this.name,
        posterPath = this.posterPath,
        backdropPath = this.backdropPath
    )

}

fun TrShowEntity.asDomainModel(): ItemModel {
    return ItemModel(
        id = this.id,
        overview = this.overview,
        name = this.name,
        posterPath = this.posterPath,
        backdropPath = this.backdropPath

    )
}