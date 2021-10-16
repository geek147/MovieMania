package com.envious.moviemania.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.envious.moviemania.databinding.ActivityMainBinding
import com.envious.moviemania.ui.adapter.ViewPagerAdapter
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val viewPager = binding.pager
        val tabLayout = binding.tabLayout

        val adapter = ViewPagerAdapter(supportFragmentManager, lifecycle)
        viewPager.adapter = adapter

        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = fragmentsTitle[position]
        }.attach()
    }
}

val fragmentsTitle = arrayOf(
    "Popular",
    "Top Rated",
    "Favorite"
)
