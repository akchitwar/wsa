package com.wsa.shows.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.wsa.shows.db.dao.FavoriteShowsDao
import com.wsa.shows.db.dao.TRShowsDao
import com.wsa.shows.db.entities.FavoriteShowEntity
import com.wsa.shows.db.entities.TrShowEntity

@Database(entities = [TrShowEntity::class, FavoriteShowEntity::class], version = 1)
@TypeConverters(Converters::class)
abstract class WSADatabase  : RoomDatabase(){
    abstract fun getRrShowsDao(): TRShowsDao
    abstract fun getFavoriteShowsDao(): FavoriteShowsDao
}