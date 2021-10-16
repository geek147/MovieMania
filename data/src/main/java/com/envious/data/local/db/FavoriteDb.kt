package com.envious.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.envious.data.local.dao.FavoriteDao
import com.envious.data.local.model.FavoriteEntity

@Database(entities = [FavoriteEntity::class], version = 1, exportSchema = false)
abstract class FavoriteDb : RoomDatabase() {
    abstract fun favoriteDao(): FavoriteDao
}
