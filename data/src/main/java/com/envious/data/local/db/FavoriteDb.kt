package com.envious.data.local.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.envious.data.local.dao.FavoriteDao
import com.envious.data.local.model.FavoriteEntity

@Database(entities = [FavoriteEntity::class], version = 1, exportSchema = false)
abstract class FavoriteDb : RoomDatabase() {

    abstract fun favoriteDao(): FavoriteDao

    companion object {

        @Volatile
        private var INSTANCE: FavoriteDb? = null

        fun getDatabase(context: Context): FavoriteDb? {
            if (INSTANCE == null) {
                synchronized(FavoriteDb::class.java) {
                    if (INSTANCE == null) {
                        INSTANCE = Room.databaseBuilder(
                            context.applicationContext,
                            FavoriteDb::class.java, "favorite_database"
                        )
                            .build()
                    }
                }
            }
            return INSTANCE
        }
    }
}
