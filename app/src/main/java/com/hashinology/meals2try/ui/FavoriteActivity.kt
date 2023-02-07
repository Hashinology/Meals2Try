package com.hashinology.meals2try.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.hashinology.meals2try.databinding.ActivityFavoritesBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class FavoriteActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFavoritesBinding
    val viewModel: MyViewModel by viewModels()

    @Inject
    lateinit var mealsAdapter: MyAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityFavoritesBinding.inflate(layoutInflater)
        binding.lifecycleOwner = this
        setContentView(binding.root)

        setRecyclerview()

        viewModel.mealsDBLiveData.observe(this, Observer {
            mealsAdapter.differ.submitList(it)
        })
    }

    private fun setRecyclerview() {
        mealsAdapter = MyAdapter()
        binding.rvFav.apply {
            adapter = mealsAdapter
            layoutManager = LinearLayoutManager(this@FavoriteActivity, LinearLayoutManager.VERTICAL, false)
        }
    }
}