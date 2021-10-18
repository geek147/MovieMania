package com.envious.data

import com.envious.data.usecase.GetFavoritesUseCase
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
class GetFavoriteUseCaseTest {

    private val repository: MovieRepository = mockk()
    private var getFavoriteUseCase: GetFavoritesUseCase = mockk()

    @Before
    fun setUp() {
        getFavoriteUseCase = GetFavoritesUseCase(repository)
    }

    @Test
    fun verify_getPopularMovie_call_getPopularMovieRepository() {

        coEvery {
            repository.getFavoriteMovies()
        } returns Result.Success(data = emptyList())

        runBlockingTest {
            getFavoriteUseCase()
        }

        coVerify {
            repository.getFavoriteMovies()
        }
    }
}
