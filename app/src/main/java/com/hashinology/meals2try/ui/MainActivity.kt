package com.hashinology.meals2try.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.hashinology.meals2try.databinding.ActivityMainBinding
import com.hashinology.meals2try.util.Resource
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel: MyViewModel by viewModels()

    @Inject
    lateinit var mealsAdapter: MyAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setRecyclerView()

        viewModel.getMeals()

        viewModel.mealsLivedata.observe(this@MainActivity, Observer { resource ->
            when(resource){
                is Resource.Loading -> {
                    showProgress()
                    hideError()
                }
                is Resource.Success -> {
                    hideProgress()
                    hideError()
                    resource.data?.let {
                        mealsAdapter.differ.submitList(it.meals)
                    }
                }
                is Resource.Error -> {
                    hideProgress()
                    resource.message?.let {
                        showError(it)
                    }
                }
            }
        })

        mealsAdapter.setOnItemclickListner {
            viewModel.addMeals(it)
            Toast.makeText(this, "Meal Added Successfuly", Toast.LENGTH_LONG)
        }

        binding.fbToFavorites.setOnClickListener{
            startActivity(Intent(this, FavoriteActivity::class.java))
        }
    }

    private fun showError(it: String) {
        binding.tvError.apply {
            visibility = View.VISIBLE
            text = it
        }
    }

    private fun hideProgress() {
        binding.pbLoading.visibility = View.INVISIBLE
    }

    private fun hideError() {
        binding.tvError.visibility = View.INVISIBLE
    }

    private fun showProgress() {
        binding.pbLoading.visibility = View.VISIBLE
    }

    private fun setRecyclerView() {
        mealsAdapter = MyAdapter()
        binding.rvPosts.apply {
            adapter = mealsAdapter
            layoutManager = GridLayoutManager(this@MainActivity, 2, GridLayoutManager.VERTICAL, false)
        }
    }
}