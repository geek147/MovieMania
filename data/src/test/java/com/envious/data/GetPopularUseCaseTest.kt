package com.envious.data

import com.envious.data.usecase.GetPopularUseCase
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
class GetPopularUseCaseTest {

    private val repository: MovieRepository = mockk()
    private var getPopularUseCase: GetPopularUseCase = mockk()

    @Before
    fun setUp() {
        getPopularUseCase = GetPopularUseCase(repository)
    }

    @Test
    fun verify_getPopularMovie_call_getPopularMovieRepository() {

        val page = 1

        coEvery {
            repository.getPopular(any())
        } returns Result.Success(data = emptyList())

        runBlockingTest {
            getPopularUseCase(
                page
            )
        }

        coVerify {
            repository.getPopular(
                page = page
            )
        }
    }
}
