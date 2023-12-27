package com.wsa.shows.obj

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
/*Except episodes view all are using ItemModel data class*/
@Parcelize
data class ItemModel (
    val id: Int? = null,
    val overview: String? = null,
    val title: String? = null,
    val posterPath: String? = null,
    val backdropPath: String? = null,
    val name: String? = null,
    ): Parcelable