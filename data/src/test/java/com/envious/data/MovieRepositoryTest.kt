package com.envious.data

import com.envious.data.local.dao.FavoriteDao
import com.envious.data.local.model.FavoriteEntity
import com.envious.data.remote.MovieApiService
import com.envious.data.remote.response.MovieResponse
import com.envious.data.repository.MovieRepositoryImpl
import com.envious.domain.model.Movie
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.justRun
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test
import retrofit2.Response

@ExperimentalCoroutinesApi
class MovieRepositoryTest {

    private val apiService: MovieApiService = mockk()
    private val favoriteDao: FavoriteDao = mockk()

    private lateinit var repositoryTest: MovieRepositoryImpl

    private val apiKey = "f287cb34edf1905969867d7701ad2bd6"
    private val page = 1
    private val language = "en-US"

    val entity = FavoriteEntity(
        adult = false,
        backdropPath = "https://google.com/1",
        id = 1,
        overview = "test",
        posterPath = "https://google.com/2",
        releaseDate = "2 Mei 2010",
        title = "Test 1",
        video = false,
        originalLanguage = "en",
        originalTitle = "Test 2 1",
        popularity = 0.0
    )

    val movie = Movie(
        adult = false,
        backdropPath = "https://google.com/1",
        id = 1,
        overview = "test",
        posterPath = "https://google.com/2",
        releaseDate = "2 Mei 2010",
        title = "Test 1",
        video = false,
        originalLanguage = "en",
        originalTitle = "Test 2 1",
        popularity = 0.0,
        isLiked = true,
        isPopularMovie = false
    )

    @Before
    fun setUp() {
        repositoryTest = MovieRepositoryImpl(apiService, favoriteDao)
    }

    @Test
    fun verify_getPopularMovie_call() {

        coEvery {
            apiService.getPopularMovie(any(), any(), any())
        } returns Response.success(
            MovieResponse()
        )

        runBlockingTest {
            repositoryTest.getPopular(page)
        }

        coVerify {
            apiService.getPopularMovie(apiKey, language, page)
        }
    }

    @Test
    fun verify_getTopRatedMovie_call() {

        coEvery {
            apiService.getTopRatedMovie(any(), any(), any())
        } returns Response.success(
            MovieResponse()
        )

        runBlockingTest {
            repositoryTest.getTopRated(page)
        }

        coVerify {
            apiService.getTopRatedMovie(apiKey, language, page)
        }
    }

    @Test
    fun verify_getFavoriteMovie_call() {

        coEvery {
            favoriteDao.getAllFavorites()
        } returns listOf(
            entity
        )

        runBlockingTest {
            repositoryTest.getFavoriteMovies()
        }

        coVerify {
            favoriteDao.getAllFavorites()
        }
    }

    @Test
    fun verify_insertFavoriteMovie_call() {

        justRun {
            favoriteDao.insert(entity)
        }

        coEvery {
            favoriteDao.getAllFavorites()
        } returns listOf(
            entity
        )

        runBlockingTest {
            repositoryTest.insertFavoriteMovie(movie)
        }

        coVerify {
            favoriteDao.insert(entity)
        }
    }

    @Test
    fun verify_deleteFavoriteMovie_call() {
        val id = 1
        justRun {
            favoriteDao.delete(id)
        }

        coEvery {
            favoriteDao.getAllFavorites()
        } returns listOf(
            entity
        )

        runBlockingTest {
            repositoryTest.deleteFavoriteMovie(id)
        }

        coVerify {
            favoriteDao.delete(id)
        }
    }
}
