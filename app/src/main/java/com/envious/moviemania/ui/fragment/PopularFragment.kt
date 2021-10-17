package com.envious.moviemania.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.envious.domain.model.Movie
import com.envious.moviemania.R
import com.envious.moviemania.base.BaseFragment
import com.envious.moviemania.databinding.FragmentPopularBinding
import com.envious.moviemania.ui.MainActivity
import com.envious.moviemania.ui.adapter.FavoriteMovieListener
import com.envious.moviemania.ui.adapter.MovieAdapter
import com.envious.moviemania.utils.EndlessRecyclerViewScrollListener
import com.envious.moviemania.utils.Intent
import com.envious.moviemania.utils.State
import com.envious.moviemania.utils.ViewState

class PopularFragment :
    BaseFragment<Intent,
        State>(),
    FavoriteMovieListener {

    private var _binding: FragmentPopularBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: MovieAdapter

    private lateinit var scrollListener: EndlessRecyclerViewScrollListener
    private var currentPage = 1

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPopularBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.state.observe(viewLifecycleOwner) {
            invalidate(it)
        }

        setupRecyclerView()
        viewModel.onIntentReceived(Intent.GetPopular)
    }

    private fun setupRecyclerView() {
        with(binding) {
            recyclerview.setHasFixedSize(true)
            val gridLayoutManager = GridLayoutManager(requireContext(), 2)
            recyclerview.layoutManager = gridLayoutManager
            recyclerview.itemAnimator = null
            adapter = MovieAdapter(requireContext(), this@PopularFragment)
            adapter.setHasStableIds(true)
            recyclerview.adapter = adapter
            scrollListener = object : EndlessRecyclerViewScrollListener(gridLayoutManager) {
                override fun onLoadMore(
                    page: Int,
                    totalItemsCount: Int,
                    view: RecyclerView?,
                ) {
                    currentPage = page + 1
                    viewModel.onIntentReceived(Intent.LoadNextPopular(currentPage))
                }
            }
            recyclerview.addOnScrollListener(scrollListener)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override val layoutResourceId: Int
        get() = R.layout.fragment_popular

    override fun invalidate(state: State) {
        with(binding) {
            pgProgressList.visibility = if (state.showLoading) View.VISIBLE else View.GONE
        }

        if (state.listFavorite.isNotEmpty()) {
            (activity as MainActivity).tabLayout.getTabAt(2)?.orCreateBadge?.number = state.listFavorite.size
        } else {
            (activity as MainActivity).tabLayout.getTabAt(2)?.removeBadge()
        }

        when (state.viewState) {
            ViewState.EmptyListFirstInit -> {
                with(binding) {
                    errorView.visibility = View.VISIBLE
                    errorView.run {
                        setUpErrorView(
                            title = resources.getString(R.string.empty_state_title),
                            message = resources.getString(R.string.empty_state_message)
                        )
                        binding.buttonRetry.setOnClickListener {
                            currentPage = 1
                            viewModel.onIntentReceived(Intent.GetPopular)
                        }
                    }
                    adapter.setData(emptyList())
                    recyclerview.visibility = View.GONE
                }
            }
            ViewState.ErrorFirstInit -> {
                with(binding) {
                    errorView.visibility = View.VISIBLE
                    errorView.run {
                        setUpErrorView()
                        binding.buttonRetry.setOnClickListener {
                            currentPage = 1
                            viewModel.onIntentReceived(Intent.GetPopular)
                        }
                    }
                    adapter.setData(emptyList())
                    recyclerview.visibility = View.GONE
                }
            }
            ViewState.ErrorLoadMore -> {
                with(binding) {
                    recyclerview.visibility = View.VISIBLE
                }
            }
            ViewState.Idle -> {}
            ViewState.SuccessFirstInit -> {
                with(binding) {
                    recyclerview.visibility = View.VISIBLE
                    adapter.setData(state.listPopular)
                    errorView.visibility = View.GONE
                }
            }
            ViewState.SuccessLoadMore -> {
                with(binding) {
                    recyclerview.visibility = View.VISIBLE
                    adapter.addData(state.listPopular)
                    errorView.visibility = View.GONE
                }
            }
            ViewState.EmptyListLoadMore -> {
                Toast.makeText(requireContext(), "new list is empty", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun insetFavoriteMovie(movie: Movie) {
        viewModel.onIntentReceived(Intent.SaveToFavorite(movie))
    }

    override fun deleteFavoriteMovie(id: Int) {
        viewModel.onIntentReceived(Intent.RemoveFromFavorite(id))
    }
}
