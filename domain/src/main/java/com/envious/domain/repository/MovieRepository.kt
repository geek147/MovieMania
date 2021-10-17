package com.envious.domain.repository

import com.envious.domain.model.Movie
import com.envious.domain.util.Result

interface MovieRepository {

    suspend fun getPopular(
        page: Int
    ): Result<List<Movie>>

    suspend fun getTopRated(
        page: Int
    ): Result<List<Movie>>

    suspend fun getFavoriteMovies(): Result<List<Movie>>

    suspend fun insertFavoriteMovie(movie: Movie): Result<List<Movie>>

    suspend fun deleteFavoriteMovie(id: Int): Result<List<Movie>>
}
