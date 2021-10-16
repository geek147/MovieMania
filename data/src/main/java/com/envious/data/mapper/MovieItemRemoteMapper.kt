package com.envious.data.mapper

import com.envious.data.remote.response.MovieItem
import com.envious.domain.model.Movie

class MovieItemRemoteMapper : BaseMapperRepository<MovieItem, Movie> {
    override fun transform(item: MovieItem): Movie = Movie(
        adult = item.adult ?: false,
        backdropPath = item.backdropPath.orEmpty(),
        id = item.id ?: 0,
        overview = item.overview.orEmpty(),
        posterPath = item.posterPath.orEmpty(),
        releaseDate = item.releaseDate.orEmpty(),
        title = item.title.orEmpty(),
        video = item.video ?: false,
        originalLanguage = item.originalLanguage.orEmpty(),
        originalTitle = item.originalTitle.orEmpty(),
        popularity = item.popularity ?: 0.0,
        isLiked = false,
        isPopularMovie = false
    )

    override fun transformToRepository(item: Movie): MovieItem =
        MovieItem()
}
