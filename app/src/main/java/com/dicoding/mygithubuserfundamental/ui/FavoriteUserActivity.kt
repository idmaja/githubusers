package com.dicoding.mygithubuserfundamental.ui

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.mygithubuserfundamental.R
import com.dicoding.mygithubuserfundamental.adapter.UsersAdapter
import com.dicoding.mygithubuserfundamental.data.local.Favorite
import com.dicoding.mygithubuserfundamental.data.response.ItemList
import com.dicoding.mygithubuserfundamental.databinding.ActivityFavoriteUserBinding
import com.dicoding.mygithubuserfundamental.viewmodel.FavoriteViewModel
import com.dicoding.mygithubuserfundamental.viewmodel.FavoriteViewModelFactory

class FavoriteUserActivity : AppCompatActivity() {

    private lateinit var favViewModel: FavoriteViewModel
    private lateinit var binding: ActivityFavoriteUserBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        initializeFavorite()

        actionBar()

        favViewModel = obtainFavoriteViewModel()
        favViewModel.fetchFavList().observe(this, ::displayFavoriteUsers)
    }

    @Suppress("DEPRECATION")
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun displayFavoriteUsers(favoriteLists: List<Favorite>) {
        val isEmpty = favoriteLists.isEmpty()
        binding.apply {
            listFavorite.visibility = if (isEmpty) {
                View.GONE
            } else {
                View.VISIBLE
            }
            if (!isEmpty) {
                val usersAdapter = UsersAdapter(favoriteLists.map { ItemList(it.username, it.avatar) })

                listFavorite.apply {
                    layoutManager = LinearLayoutManager(this@FavoriteUserActivity)
                    setHasFixedSize(true)
                    adapter = usersAdapter
                }
            }
            if (isEmpty) {
                binding.tvFavoriteStatus.visibility = View.VISIBLE
            }
        }
    }

    private fun initializeFavorite() {
        binding = ActivityFavoriteUserBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    private fun actionBar() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = getString(R.string.favorite_user)
    }

    private fun obtainFavoriteViewModel(): FavoriteViewModel {
        val favFactory = FavoriteViewModelFactory.getInstance(application)
        return ViewModelProvider(this, favFactory)[FavoriteViewModel::class.java]
    }
}