package com.envious.data.usecase

import com.envious.domain.model.Movie
import com.envious.domain.repository.MovieRepository
import com.envious.domain.usecase.BaseCaseWrapper
import com.envious.domain.util.Result
import javax.inject.Inject

class InsertToFavoriteUseCase @Inject constructor(
    private val movieRepository: MovieRepository
) : BaseCaseWrapper<List<Movie>, InsertToFavoriteUseCase.Params>() {

    override suspend fun build(params: Params?): Result<List<Movie>> {
        if (params == null) throw IllegalArgumentException("Params should not be null")

        return movieRepository.insertFavoriteMovie(params.movie)
    }

    class Params(val movie: Movie)
}
