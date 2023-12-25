package com.wsa.shows.db

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.wsa.shows.db.entities.TrShowEntity

@ProvidedTypeConverter
class Converters{

    @TypeConverter
    fun fromTrendingShowsJson(value: String): List<TrShowEntity> {
        val listType = object : TypeToken<List<TrShowEntity>>() {}.type
        return Gson().fromJson(value, listType)?: emptyList<TrShowEntity>()
    }

    @TypeConverter
    fun toTrendingShowsJson(list: List<TrShowEntity>): String {
        val gson = Gson()
        return gson.toJson(list)
    }
}