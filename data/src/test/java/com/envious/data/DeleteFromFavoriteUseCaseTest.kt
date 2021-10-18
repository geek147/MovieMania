package com.envious.data

import com.envious.data.usecase.DeleteFromFavoriteUseCase
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
class DeleteFromFavoriteUseCaseTest {

    private val repository: MovieRepository = mockk()
    private var deleteFromFavoriteUseCase: DeleteFromFavoriteUseCase = mockk()

    @Before
    fun setUp() {
        deleteFromFavoriteUseCase = DeleteFromFavoriteUseCase(repository)
    }

    @Test
    fun verify_deleteFavoriteMovie_call_deleteFavoriteMovieRepository() {
        val id = 1

        coEvery {
            repository.deleteFavoriteMovie(any())
        } returns Result.Success(data = emptyList())

        runBlockingTest {
            deleteFromFavoriteUseCase(
                id
            )
        }

        coVerify {
            repository.deleteFavoriteMovie(
                id
            )
        }
    }
}
