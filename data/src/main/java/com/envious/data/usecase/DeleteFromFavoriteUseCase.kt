package com.envious.data.usecase

import com.envious.domain.repository.MovieRepository
import com.envious.domain.usecase.BaseCaseWrapper
import com.envious.domain.util.Result
import javax.inject.Inject

class DeleteFromFavoriteUseCase @Inject constructor(
    private val movieRepository: MovieRepository
) : BaseCaseWrapper<Unit, Int>() {

    override suspend fun build(params: Int?): Result<Unit> {
        if (params == null) throw IllegalArgumentException("Params should not be null")

        return movieRepository.deleteFavoriteMovie(params)
    }
}
