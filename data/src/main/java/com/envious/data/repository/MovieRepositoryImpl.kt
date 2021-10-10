package com.envious.data.repository

import android.util.Log
import com.envious.data.local.dao.FavoriteDao
import com.envious.data.local.model.FavoriteEntity
import com.envious.data.mapper.MovieItemLocalMapper
import com.envious.data.mapper.MovieItemRemoteMapper
import com.envious.data.remote.MovieApiService
import com.envious.domain.model.Movie
import com.envious.domain.repository.MovieRepository
import com.envious.domain.util.Result
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val movieApiService: MovieApiService,
    private val favoriteDao: FavoriteDao
) : MovieRepository {

    override suspend fun getPopular(
        apiKey: String,
        language: String,
        page: Int,
    ): Result<List<Movie>> {
        return try {
            val result = movieApiService.getPopularMovie(
                apiKey = apiKey,
                language = language,
                page = page
            )
            if (result.isSuccessful) {
                val remoteMapper = MovieItemRemoteMapper()
                val remoteData = result.body()
                val items = remoteData?.movieItems
                return if (remoteData != null && !items.isNullOrEmpty()) {
                    val listData = mutableListOf<Movie>()
                    items.forEach {
                        listData.add(remoteMapper.transform(it!!))
                    }
                    Result.Success(listData)
                } else {
                    Result.Success(emptyList())
                }
            } else {
                return Result.Error(Exception("Invalid data/failure"))
            }
        } catch (e: Exception) {
            Log.e("ApiCalls", "Call error: ${e.localizedMessage}", e.cause)
            Result.Error(Exception(e.cause))
        }
    }

    override suspend fun getTopRated(
        apiKey: String,
        language: String,
        page: Int,
    ): Result<List<Movie>> {
        return try {
            val result = movieApiService.getTopRatedMovie(
                apiKey = apiKey,
                language = language,
                page = page
            )
            if (result.isSuccessful) {
                val remoteMapper = MovieItemRemoteMapper()
                val remoteData = result.body()
                val items = remoteData?.movieItems
                return if (remoteData != null && !items.isNullOrEmpty()) {
                    val listData = mutableListOf<Movie>()
                    items.forEach {
                        listData.add(remoteMapper.transform(it!!))
                    }
                    Result.Success(listData)
                } else {
                    Result.Success(emptyList())
                }
            } else {
                return Result.Error(Exception("Invalid data/failure"))
            }
        } catch (e: Exception) {
            Log.e("ApiCalls", "Call error: ${e.localizedMessage}", e.cause)
            Result.Error(Exception(e.cause))
        }
    }

    override suspend fun getFavoriteMovies(): List<Movie> {
        val localMapper = MovieItemLocalMapper()
        val itemsFavorite = favoriteDao.getAllFavorites()
        val listData = mutableListOf<Movie>()

        itemsFavorite.forEach {
            listData.add(localMapper.transform(it!!))
        }
        return listData.toList()
    }

    override suspend fun insertFavoriteMovie(movie: Movie) {
        favoriteDao.insert(
            FavoriteEntity(
                id = movie.id,
                adult = movie.adult,
                backdropPath = movie.backdropPath,
                originalLanguage = movie.originalLanguage,
                originalTitle = movie.originalTitle,
                overview = movie.overview,
                popularity = movie.popularity,
                posterPath = movie.posterPath,
                releaseDate = movie.releaseDate,
                title = movie.title,
                video = movie.video
            )
        )
    }

    override suspend fun getFavoriteMovieById(id: Int): Movie {
        val localMapper = MovieItemLocalMapper()
        val item = favoriteDao.getFavoriteMovieById(id)
        return localMapper.transform(item!!)
    }

    override suspend fun deleteFavoriteMovie(id: Int) {
        favoriteDao.delete(id)
    }
}
