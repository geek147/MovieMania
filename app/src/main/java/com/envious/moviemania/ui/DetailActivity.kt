package com.envious.moviemania.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.envious.data.BuildConfig.BACKDROP_URL
import com.envious.data.BuildConfig.IMAGE_URL
import com.envious.domain.model.Movie
import com.envious.moviemania.databinding.ActivityDetailBinding
import com.envious.moviemania.utils.BindingConverters

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding

    companion object {
        private const val BUNDLE_KEY_MOVIE = "BUNDLE_KEY_MOVIE"

        fun createIntent(
            context: Context,
            movie: Movie
        ): Intent {
            return Intent(context, DetailActivity::class.java).apply {
                putExtra(BUNDLE_KEY_MOVIE, movie)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val intent: Intent = intent
        val movie = intent.getParcelableExtra<Movie>(BUNDLE_KEY_MOVIE)

        supportActionBar?.title = movie?.title.orEmpty()

        with(binding) {
            tvTitle.text = movie?.title.orEmpty()
            tvOverview.text = movie?.overview.orEmpty()
            tvReleaseDate.text = movie?.releaseDate.orEmpty()
            BindingConverters.loadImage(ivPoster, IMAGE_URL + movie?.posterPath)
            BindingConverters.loadImage(ivBackdrop, BACKDROP_URL + movie?.backdropPath)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
