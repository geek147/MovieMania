package com.envious.data.usecase

import com.envious.domain.model.Movie
import com.envious.domain.repository.MovieRepository
import com.envious.domain.usecase.BaseCaseWrapper
import com.envious.domain.util.Result
import javax.inject.Inject

class GetFavoritesUseCase @Inject constructor(
    private val movieRepository: MovieRepository
) : BaseCaseWrapper<List<Movie>, Unit>() {
    override suspend fun build(params: Unit?): Result<List<Movie>> {
        return movieRepository.getFavoriteMovies()
    }
}
