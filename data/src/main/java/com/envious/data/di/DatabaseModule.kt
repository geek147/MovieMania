package com.envious.data.di

import android.content.Context
import androidx.room.Room
import com.envious.data.local.dao.FavoriteDao
import com.envious.data.local.db.FavoriteDb
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class DatabaseModule {
    @Provides
    fun provideFavoriteDao(favoriteDb: FavoriteDb): FavoriteDao {
        return favoriteDb.favoriteDao()
    }

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext appContext: Context): FavoriteDb {
        return Room.databaseBuilder(
            appContext,
            FavoriteDb::class.java,
            "favorite_db"
        ).build()
    }
}
