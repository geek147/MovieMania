package com.envious.domain.usecase

import com.envious.domain.model.Movie
import com.envious.domain.util.Result

interface GetPopularUseCase {
    suspend operator fun invoke(
        apiKey: String,
        language: String,
        page: Int
    ): Result<List<Movie>>
}
