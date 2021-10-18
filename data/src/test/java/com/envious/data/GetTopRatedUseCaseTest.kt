package com.envious.data

import com.envious.data.usecase.GetTopRatedUseCase
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
class GetTopRatedUseCaseTest {

    private val repository: MovieRepository = mockk()
    private var getTopRatedUseCase: GetTopRatedUseCase = mockk()

    @Before
    fun setUp() {
        getTopRatedUseCase = GetTopRatedUseCase(repository)
    }

    @Test
    fun verify_getTopRatedMovie_call_getTopRatedMovieRepository() {

        val page = 1

        coEvery {
            repository.getTopRated(any())
        } returns Result.Success(data = emptyList())

        runBlockingTest {
            getTopRatedUseCase(
                page
            )
        }

        coVerify {
            repository.getTopRated(
                page = page
            )
        }
    }
}
