package com.envious.data.usecase

import com.envious.domain.model.Movie
import com.envious.domain.repository.MovieRepository
import com.envious.domain.usecase.BaseCaseWrapper
import com.envious.domain.util.Result
import javax.inject.Inject

class DeleteFromFavoriteUseCase @Inject constructor(
    private val movieRepository: MovieRepository
) : BaseCaseWrapper<List<Movie>, Int>() {

    override suspend fun build(params: Int?): Result<List<Movie>> {
        if (params == null) throw IllegalArgumentException("Params should not be null")

        return movieRepository.deleteFavoriteMovie(params)
    }
}
