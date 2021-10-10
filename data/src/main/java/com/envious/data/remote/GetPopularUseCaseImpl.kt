package com.envious.data.remote

import com.envious.domain.model.Movie
import com.envious.domain.repository.MovieRepository
import com.envious.domain.usecase.GetPopularUseCase
import com.envious.domain.util.Result
import javax.inject.Inject

class GetPopularUseCaseImpl @Inject constructor(
    private val repository: MovieRepository
) : GetPopularUseCase {

    override suspend fun invoke(
        apiKey: String,
        language: String,
        page: Int
    ): Result<List<Movie>> {
        return repository.getPopular(apiKey, language, page)
    }
}
