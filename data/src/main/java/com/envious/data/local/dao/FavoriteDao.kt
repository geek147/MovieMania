package com.envious.data.local.dao

import androidx.room.* // ktlint-disable no-wildcard-imports
import com.envious.data.local.model.FavoriteEntity

@Dao
interface FavoriteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(movie: FavoriteEntity)

    @Query("SELECT * FROM favorite_table")
    fun getAllFavorites(): List<FavoriteEntity?>

    @Query("DELETE FROM favorite_table WHERE id = :id")
    fun delete(id: Int)
}
