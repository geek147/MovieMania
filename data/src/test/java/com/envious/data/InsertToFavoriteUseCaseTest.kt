package com.envious.data

import com.envious.data.usecase.InsertToFavoriteUseCase
import com.envious.domain.model.Movie
import com.envious.domain.repository.MovieRepository
import com.envious.domain.util.Result
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class InsertToFavoriteUseCaseTest {

    private val repository: MovieRepository = mockk()
    private var insertToFavoriteUseCase: InsertToFavoriteUseCase = mockk()

    @Before
    fun setUp() {
        insertToFavoriteUseCase = InsertToFavoriteUseCase(repository)
    }

    @Test
    fun verify_insertFavoriteMovie_call_insertFavoriteMovieRepository() {
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

        coEvery {
            repository.insertFavoriteMovie(any())
        } returns Result.Success(data = emptyList())

        val param = InsertToFavoriteUseCase.Params(movie)

        runBlockingTest {
            insertToFavoriteUseCase(
                param
            )
        }

        coVerify {
            repository.insertFavoriteMovie(
                movie
            )
        }
    }
}
