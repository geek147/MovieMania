package com.envious.domain.repository

import com.envious.domain.model.Movie
import com.envious.domain.util.Result

interface MovieRepository {

    suspend fun getPopular(
        apiKey: String,
        language: String,
        page: Int
    ): Result<List<Movie>>

    suspend fun getTopRated(
        apiKey: String,
        language: String,
        page: Int
    ): Result<List<Movie>>

    suspend fun getFavoriteMovies(): List<Movie>

    suspend fun insertFavoriteMovie(movie: Movie)

    suspend fun getFavoriteMovieById(id: Int): Movie

    suspend fun deleteFavoriteMovie(id: Int)
}
