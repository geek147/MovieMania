package com.envious.data.remote

import com.envious.domain.model.Movie
import com.envious.domain.repository.MovieRepository
import com.envious.domain.usecase.GetTopRatedUseCase
import com.envious.domain.util.Result
import javax.inject.Inject

class GetTopRatedUseCaseImpl @Inject constructor(
    private val repository: MovieRepository
) : GetTopRatedUseCase {

    override suspend fun invoke(
        apiKey: String,
        language: String,
        page: Int
    ): Result<List<Movie>> {
        return repository.getTopRated(apiKey, language, page)
    }
}
