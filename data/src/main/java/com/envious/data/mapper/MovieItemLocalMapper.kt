package com.envious.data.mapper

import com.envious.data.local.model.FavoriteEntity
import com.envious.domain.model.Movie

class MovieItemLocalMapper : BaseMapperRepository<FavoriteEntity, Movie> {
    override fun transform(item: FavoriteEntity): Movie = Movie(
        adult = item.adult ?: false,
        backdropPath = item.backdropPath.orEmpty(),
        id = item.id,
        overview = item.overview.orEmpty(),
        posterPath = item.posterPath.orEmpty(),
        releaseDate = item.releaseDate.orEmpty(),
        title = item.title.orEmpty(),
        video = item.video ?: false,
        originalLanguage = item.originalLanguage.orEmpty(),
        originalTitle = item.originalTitle.orEmpty(),
        popularity = item.popularity ?: 0.0
    )

    override fun transformToRepository(item: Movie): FavoriteEntity = FavoriteEntity(
        adult = item.adult ?: false,
        backdropPath = item.backdropPath.orEmpty(),
        id = item.id,
        overview = item.overview.orEmpty(),
        posterPath = item.posterPath.orEmpty(),
        releaseDate = item.releaseDate.orEmpty(),
        title = item.title.orEmpty(),
        video = item.video ?: false,
        originalLanguage = item.originalLanguage.orEmpty(),
        originalTitle = item.originalTitle.orEmpty(),
        popularity = item.popularity ?: 0.0
    )
}
