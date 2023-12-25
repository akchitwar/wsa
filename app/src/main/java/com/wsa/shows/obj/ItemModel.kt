package com.wsa.shows.obj

data class ItemModel (
    val id: Int? = null,
    val overview: String? = null,
    val originalLanguage: String? = null,
    val originalTitle: String? = null,
    val title: String? = null,
    //val genreIds: List<Int?>? = null,
    val posterPath: String? = null,
    val backdropPath: String? = null,
    val originalName: String? = null,
    val name: String? = null,
    var favorite: Boolean? = false,
    var posterPathBase64: String? = null,
    var backdropPathBase64: String? = null
    )