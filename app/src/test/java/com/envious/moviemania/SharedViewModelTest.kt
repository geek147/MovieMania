package com.envious.moviemania

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.envious.data.dispatchers.CoroutineDispatchers
import com.envious.data.usecase.* // ktlint-disable no-wildcard-imports
import com.envious.domain.model.Movie
import com.envious.domain.util.Result
import com.envious.moviemania.ui.viewmodel.SharedViewModel
import com.envious.moviemania.utils.Intent
import com.envious.moviemania.utils.State
import com.envious.moviemania.utils.ViewState
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import io.mockk.slot
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class SharedViewModelTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private var getPopular = mockk<GetPopularUseCase>()
    private var getTopRated = mockk<GetTopRatedUseCase>()
    private var getFavorite = mockk<GetFavoritesUseCase>()
    private var insertToFavorite = mockk<InsertToFavoriteUseCase>()
    private var deleteFromFavorite = mockk<DeleteFromFavoriteUseCase>()
    private var ioDispatcher = mockk<CoroutineDispatchers>()

    private val observedStateList = mutableListOf<State>()
    private val observerState = mockk<Observer<State>>()
    private val slotState = slot<State>()

    private val testDispatcher = TestCoroutineDispatcher()

    private val viewModel = SharedViewModel(
        getPopularUseCase = getPopular,
        getFavoritesUseCase = getFavorite,
        getTopRatedUseCase = getTopRated,
        insertToFavoriteUseCase = insertToFavorite,
        deleteFromFavoriteUseCase = deleteFromFavorite,
        ioDispatchers = ioDispatcher
    )

    val movie = Movie(
        adult = false,
        backdropPath = "https://google.com/1",
        id = 1,
        overview = "test",
        posterPath = "https://google.com/2",
        releaseDate = "2 Mei 2010",
        title = "Test 1",
        video = false,
        originalLanguage = "en",
        originalTitle = "Test 2 1",
        popularity = 0.0,
        isLiked = true,
        isPopularMovie = false
    )

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)

        viewModel.state.observeForever(observerState)

        every {
            observerState.onChanged(capture(slotState))
        } answers {
            observedStateList.add(slotState.captured)
        }
    }

    @After
    fun tearDown() {
        observedStateList.clear()

        viewModel.state.removeObserver(observerState)
        testDispatcher.cleanupTestCoroutines()
    }

    @Test
    fun `onGetPopular loading showLoading`() {

        coEvery {
            ioDispatcher.io
        } returns testDispatcher

        coEvery {
            getPopular(any())
        } returns Result.Success(data = emptyList())

        viewModel.onIntentReceived(
            Intent.GetPopular
        )

        assertEquals(observedStateList.first().showLoading, true)
    }

    @Test
    fun `onGetPopular success should set view state to Success  `() {

        coEvery {
            ioDispatcher.io
        } returns testDispatcher

        coEvery {
            getPopular(any())
        } returns Result.Success(listOf(movie))

        coEvery {
            getFavorite()
        } returns Result.Success(data = listOf(movie))

        viewModel.onIntentReceived(
            Intent.GetPopular
        )

        assertEquals(observedStateList.last().showLoading, false)
        assertEquals(observedStateList.last().viewState, ViewState.SuccessFirstInit)
        assertEquals(observedStateList.last().listPopular[0].id, 1)
    }

    @Test
    fun `onGetPopular success but empty should set view state to EmptyState  `() {

        coEvery {
            ioDispatcher.io
        } returns testDispatcher

        coEvery {
            getPopular(any())
        } returns Result.Success(emptyList())

        coEvery {
            getFavorite()
        } returns Result.Success(data = listOf(movie))

        viewModel.onIntentReceived(
            Intent.GetPopular
        )

        assertEquals(observedStateList.last().showLoading, false)
        assertEquals(observedStateList.last().viewState, ViewState.EmptyListFirstInit)
    }

    @Test
    fun `onGetTopRated loading showLoading`() {

        coEvery {
            ioDispatcher.io
        } returns testDispatcher

        coEvery {
            getTopRated(any())
        } returns Result.Success(data = emptyList())

        viewModel.onIntentReceived(
            Intent.GetTopRated
        )

        assertEquals(observedStateList.first().showLoading, true)
    }

    @Test
    fun `onGetTopRated success should set view state to Success  `() {

        coEvery {
            ioDispatcher.io
        } returns testDispatcher

        coEvery {
            getTopRated(any())
        } returns Result.Success(listOf(movie))

        viewModel.onIntentReceived(
            Intent.GetTopRated
        )

        assertEquals(observedStateList.last().showLoading, false)
        assertEquals(observedStateList.last().viewState, ViewState.SuccessFirstInit)
        assertEquals(observedStateList.last().listTopRated[0].id, 1)
    }

    @Test
    fun `onGetTopRated success but empty should set view state to EmptyState  `() {

        coEvery {
            ioDispatcher.io
        } returns testDispatcher

        coEvery {
            getTopRated(any())
        } returns Result.Success(emptyList())

        viewModel.onIntentReceived(
            Intent.GetTopRated
        )

        assertEquals(observedStateList.last().showLoading, false)
        assertEquals(observedStateList.last().viewState, ViewState.EmptyListFirstInit)
    }

    @Test
    fun `onGetFavorite loading showLoading`() {

        coEvery {
            ioDispatcher.io
        } returns testDispatcher

        coEvery {
            getFavorite()
        } returns Result.Success(data = emptyList())

        viewModel.onIntentReceived(
            Intent.GetTopRated
        )

        assertEquals(observedStateList.first().showLoading, true)
    }

    @Test
    fun `onGetFavorite success should set view state to Success  `() {

        coEvery {
            ioDispatcher.io
        } returns testDispatcher

        coEvery {
            getFavorite()
        } returns Result.Success(listOf(movie))

        viewModel.onIntentReceived(
            Intent.GetFavorites
        )

        assertEquals(observedStateList.last().showLoading, false)
        assertEquals(observedStateList.last().viewState, ViewState.SuccessFirstInit)
        assertEquals(observedStateList.last().listFavorite[0].id, 1)
    }

    @Test
    fun `onGetFavorite success but empty should set view state to EmptyState  `() {

        coEvery {
            ioDispatcher.io
        } returns testDispatcher

        coEvery {
            getFavorite()
        } returns Result.Success(emptyList())

        viewModel.onIntentReceived(
            Intent.GetFavorites
        )

        assertEquals(observedStateList.last().showLoading, false)
        assertEquals(observedStateList.last().viewState, ViewState.EmptyListFirstInit)
    }
}
