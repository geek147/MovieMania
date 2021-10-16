package com.envious.moviemania.utils

import com.envious.domain.model.Movie

sealed class Intent {
    object GetTopRated : Intent()
    object GetPopular : Intent()
    object GetFavorites : Intent()
    data class LoadNextTopRated(val page: Int) : Intent()
    data class LoadNextPopular(val page: Int) : Intent()
    data class SaveToFavorite(val movie: Movie) : Intent()
    data class RemoveFromFavorite(val id: Int) : Intent()
}

data class State(
    val showLoading: Boolean = false,
    val listTopRated: List<Movie> = listOf(),
    val listPopular: List<Movie> = listOf(),
    val listFavorite: List<Movie> = listOf(),
    val viewState: ViewState = ViewState.Idle
)

sealed class ViewState {
    object Idle : ViewState()
    object SuccessFirstInit : ViewState()
    object EmptyListFirstInit : ViewState()
    object EmptyListLoadMore : ViewState()
    object SuccessLoadMore : ViewState()
    object ErrorFirstInit : ViewState()
    object ErrorLoadMore : ViewState()
}
