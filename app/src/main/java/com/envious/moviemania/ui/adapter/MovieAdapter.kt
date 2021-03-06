package com.envious.moviemania.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.envious.data.BuildConfig.IMAGE_URL
import com.envious.domain.model.Movie
import com.envious.moviemania.R
import com.envious.moviemania.databinding.ListItemRowBinding
import com.envious.moviemania.ui.DetailActivity
import com.envious.moviemania.utils.BindingConverters

class MovieAdapter(private var context: Context, private var favoriteMovieListener: FavoriteMovieListener) : RecyclerView.Adapter<MovieAdapter.MainViewHolder>() {
    private var listMovies: MutableList<Movie> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val binding: ListItemRowBinding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.list_item_row, parent, false)
        return MainViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return if (listMovies.isNullOrEmpty()) {
            0
        } else {
            listMovies.size
        }
    }

    override fun getItemId(position: Int): Long {
        val movie: Movie = listMovies[position]
        return movie.id.toLong()
    }

    fun addData(list: List<Movie>) {
        this.listMovies.addAll(list)
        notifyDataSetChanged()
    }

    fun setData(list: List<Movie>) {
        this.listMovies.clear()
        this.listMovies.addAll(list)
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        listMovies[holder.bindingAdapterPosition].let {
            holder.bindData(it, context, favoriteMovieListener)
        }
    }

    class MainViewHolder(private val binding: ListItemRowBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bindData(model: Movie, context: Context, favoriteMovieListener: FavoriteMovieListener) {
            BindingConverters.loadImage(binding.ivMoviePoster, IMAGE_URL + model.posterPath)

            binding.movie = model
            binding.tgFavorite.setOnCheckedChangeListener { buttonView, isChecked ->
                if (isChecked) {
                    binding.tgFavorite.setBackgroundResource(R.drawable.star_yellow)
                    model.isLiked = true
                    favoriteMovieListener.insetFavoriteMovie(model)
                } else {
                    binding.tgFavorite.setBackgroundResource(R.drawable.star_grey)
                    model.isLiked = false
                    favoriteMovieListener.deleteFavoriteMovie(model.id)
                }
            }
            itemView.setOnClickListener {
                context.startActivity(
                    DetailActivity.createIntent(context, model)
                )
            }
        }
    }
}

interface FavoriteMovieListener {
    fun insetFavoriteMovie(movie: Movie)
    fun deleteFavoriteMovie(id: Int)
}
