package com.envious.moviemania.ui.viewmodel

import androidx.lifecycle.viewModelScope
import com.envious.data.dispatchers.CoroutineDispatchers
import com.envious.data.usecase.* // ktlint-disable no-wildcard-imports
import com.envious.domain.model.Movie
import com.envious.domain.util.Result
import com.envious.moviemania.utils.Intent
import com.envious.moviemania.utils.State
import com.envious.moviemania.utils.ViewState
import com.envious.searchphoto.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class SharedViewModel @Inject constructor(
    private val getTopRatedUseCase: GetTopRatedUseCase,
    private val getPopularUseCase: GetPopularUseCase,
    private val getFavoritesUseCase: GetFavoritesUseCase,
    private val insertToFavoriteUseCase: InsertToFavoriteUseCase,
    private val deleteFromFavoriteUseCase: DeleteFromFavoriteUseCase,
    private val ioDispatchers: CoroutineDispatchers,
) : BaseViewModel<Intent, State>(State()) {

    override fun onIntentReceived(intent: Intent) {
        when (intent) {
            Intent.GetFavorites -> {
                getFavorites()
            }
            Intent.GetPopular -> getPopularFavorites(1, false)
            Intent.GetTopRated -> getTopRatedMovie(1, false)
            is Intent.LoadNextPopular -> getPopularFavorites(intent.page, true)
            is Intent.LoadNextTopRated -> getTopRatedMovie(intent.page, true)
            is Intent.RemoveFromFavorite -> removeFromFavorite(intent.id)
            is Intent.SaveToFavorite -> saveToFavorite(intent.movie)
        }
    }

    private fun saveToFavorite(movie: Movie) {
        val params = InsertToFavoriteUseCase.Params(movie)
        viewModelScope.launch {
            when (
                val result = withContext(ioDispatchers.io) {
                    insertToFavoriteUseCase(params)
                }
            ) {
                is Result.Success -> {
                    if (result.data.isEmpty()) {
                        setState {
                            copy(
                                listFavorite = emptyList(),
                                showLoading = false,
                            )
                        }
                    } else {
                        setState {
                            copy(
                                listFavorite = result.data,
                                showLoading = false,
                            )
                        }
                    }
                }
                is Result.Error -> {
                    setState {
                        copy(
                            listFavorite = emptyList(),
                            showLoading = false,
                        )
                    }
                }
            }
        }
    }

    private fun removeFromFavorite(id: Int) {
        viewModelScope.launch {
            when (
                val result = withContext(ioDispatchers.io) {
                    deleteFromFavoriteUseCase(id)
                }
            ) {
                is Result.Success -> {
                    if (result.data.isEmpty()) {
                        setState {
                            copy(
                                listFavorite = emptyList(),
                                showLoading = false,
                            )
                        }
                    } else {
                        setState {
                            copy(
                                listFavorite = result.data,
                                showLoading = false,
                            )
                        }
                    }
                }
                is Result.Error -> {
                    setState {
                        copy(
                            listFavorite = emptyList(),
                            showLoading = false,
                        )
                    }
                }
            }
        }
    }

    private fun getTopRatedMovie(page: Int, isLoadMore: Boolean) {
        setState {
            copy(
                showLoading = true,
            )
        }

        viewModelScope.launch {
            when (
                val result = withContext(ioDispatchers.io) {
                    getTopRatedUseCase(page)
                }
            ) {
                is Result.Success -> {
                    if (isLoadMore) {
                        if (result.data.isEmpty()) {
                            setState {
                                copy(
                                    listTopRated = emptyList(),
                                    showLoading = false,
                                    viewState = ViewState.EmptyListLoadMore
                                )
                            }
                        } else {
                            setState {
                                copy(
                                    listTopRated = result.data,
                                    showLoading = false,
                                    viewState = ViewState.SuccessLoadMore
                                )
                            }
                        }
                    } else {
                        if (result.data.isEmpty()) {
                            setState {
                                copy(
                                    listTopRated = emptyList(),
                                    showLoading = false,
                                    viewState = ViewState.EmptyListFirstInit
                                )
                            }
                        } else {
                            setState {
                                copy(
                                    listTopRated = result.data,
                                    showLoading = false,
                                    viewState = ViewState.SuccessFirstInit
                                )
                            }
                        }
                    }
                }
                is Result.Error -> {
                    if (isLoadMore) {
                        setState {
                            copy(
                                listTopRated = emptyList(),
                                showLoading = false,
                                viewState = ViewState.ErrorLoadMore
                            )
                        }
                    } else {
                        setState {
                            copy(
                                listTopRated = emptyList(),
                                showLoading = false,
                                viewState = ViewState.ErrorFirstInit
                            )
                        }
                    }
                }
            }
        }
    }

    private fun getPopularMovie(page: Int, isLoadMore: Boolean, favorites: List<Movie>) {
        viewModelScope.launch {

            when (
                val result = withContext(ioDispatchers.io) {
                    getPopularUseCase(page)
                }
            ) {
                is Result.Success -> {
                    if (result.data.isNotEmpty()) {
                        result.data.forEach { movie ->
                            favorites.forEach { favorite ->
                                if (movie.id == favorite.id) movie.isLiked = true else false
                            }
                        }
                    }

                    if (isLoadMore) {
                        if (result.data.isEmpty()) {
                            setState {
                                copy(
                                    listPopular = emptyList(),
                                    showLoading = false,
                                    viewState = ViewState.EmptyListLoadMore
                                )
                            }
                        } else {
                            setState {
                                copy(
                                    listPopular = result.data,
                                    showLoading = false,
                                    viewState = ViewState.SuccessLoadMore
                                )
                            }
                        }
                    } else {
                        if (result.data.isEmpty()) {
                            setState {
                                copy(
                                    listPopular = emptyList(),
                                    showLoading = false,
                                    viewState = ViewState.EmptyListFirstInit
                                )
                            }
                        } else {
                            setState {
                                copy(
                                    listPopular = result.data,
                                    showLoading = false,
                                    viewState = ViewState.SuccessFirstInit
                                )
                            }
                        }
                    }
                }
                is Result.Error -> {
                    if (isLoadMore) {
                        setState {
                            copy(
                                listPopular = emptyList(),
                                showLoading = false,
                                viewState = ViewState.ErrorLoadMore
                            )
                        }
                    } else {
                        setState {
                            copy(
                                listPopular = emptyList(),
                                showLoading = false,
                                viewState = ViewState.ErrorFirstInit
                            )
                        }
                    }
                }
            }
        }
    }

    private fun getPopularFavorites(page: Int, isLoadMore: Boolean) {
        setState {
            copy(
                showLoading = true,
            )
        }

        viewModelScope.launch {
            when (
                val result = withContext(ioDispatchers.io) {
                    getFavoritesUseCase()
                }
            ) {
                is Result.Success -> {
                    getPopularMovie(page, isLoadMore, result.data)
                    setState {
                        copy(
                            listFavorite = result.data,
                        )
                    }
                }
                is Result.Error -> {
                    getPopularMovie(page, isLoadMore, emptyList())
                    setState {
                        copy(
                            listFavorite = emptyList(),
                        )
                    }
                }
            }
        }
    }

    private fun getFavorites() {
        setState {
            copy(
                showLoading = true,
            )
        }

        viewModelScope.launch {

            when (
                val result = withContext(ioDispatchers.io) {
                    getFavoritesUseCase()
                }
            ) {
                is Result.Success -> {
                    if (result.data.isEmpty()) {
                        setState {
                            copy(
                                listFavorite = emptyList(),
                                showLoading = false,
                                viewState = ViewState.EmptyListFirstInit
                            )
                        }
                    } else {
                        setState {
                            copy(
                                listFavorite = result.data,
                                showLoading = false,
                                viewState = ViewState.SuccessFirstInit
                            )
                        }
                    }
                }
                is Result.Error -> {
                    setState {
                        copy(
                            listFavorite = emptyList(),
                            showLoading = false,
                            viewState = ViewState.ErrorFirstInit
                        )
                    }
                }
            }
        }
    }
}
