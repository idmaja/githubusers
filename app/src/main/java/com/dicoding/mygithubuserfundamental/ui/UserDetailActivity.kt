package com.dicoding.mygithubuserfundamental.ui

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.dicoding.mygithubuserfundamental.R
import com.dicoding.mygithubuserfundamental.adapter.DetailUserAdapter
import com.dicoding.mygithubuserfundamental.data.response.DetailUserResponse
import com.dicoding.mygithubuserfundamental.data.response.ItemList
import com.dicoding.mygithubuserfundamental.databinding.ActivityUserDetailBinding
import com.dicoding.mygithubuserfundamental.viewmodel.FavoriteViewModel
import com.dicoding.mygithubuserfundamental.viewmodel.FavoriteViewModelFactory
import com.dicoding.mygithubuserfundamental.viewmodel.UsersViewModel
import com.dicoding.mygithubuserfundamental.viewmodel.UsersViewModelFactory
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Suppress("DEPRECATION")
class UserDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityUserDetailBinding
    private lateinit var usersViewModel: UsersViewModel
    private lateinit var favoriteViewModel: FavoriteViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityUserDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupTabLayout()

        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            title = "Detail User"
        }

        fetchUserViewModel()
        fetchFavoriteViewModel()

    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun setupTabLayout() {
        val sectionPagerAdapter = DetailUserAdapter(this)
        val viewPager: ViewPager2 = findViewById(R.id.view_pager)
        viewPager.adapter = sectionPagerAdapter
        val tabLayout: TabLayout = findViewById(R.id.tabs)
        TabLayoutMediator(tabLayout, viewPager) {
            tab, position -> tab.text =
                getString(intArrayOf(
                    R.string.followers,
                    R.string.following)[position]
                )
        }.attach()
    }

    private fun fetchUserViewModel(){
        val intentViewModel = intent.getParcelableExtra<ItemList>("username")
        val userViewModelFactory = UsersViewModelFactory(intentViewModel?.username)
        usersViewModel = ViewModelProvider(this, userViewModelFactory)[UsersViewModel::class.java]
        usersViewModel.users.observe(this) { users -> initUsers(users) }
        usersViewModel.isLoading.observe(this) { isLoading -> showLoading(isLoading) }
    }

    private fun fetchFavoriteViewModel(){
        val intentViewModel = intent.getParcelableExtra<ItemList>("username")
        favoriteViewModel = callViewModel(this@UserDetailActivity)
        intentViewModel?.let { initFavorite(it) }
    }

    private fun showLoading(isLoading: Boolean){
        binding.apply { progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE }
    }

    private fun callViewModel(activity: AppCompatActivity): FavoriteViewModel{
        val favoriteViewModelFactory = FavoriteViewModelFactory
            .getInstance(activity.application)

        return ViewModelProvider(
            activity, favoriteViewModelFactory
        )[FavoriteViewModel::class.java]
    }

    private fun initFavorite(data: ItemList?){
        lifecycleScope.launch(Dispatchers.IO) {
            if (data != null) {
                val isFavorite = favoriteViewModel.fetchIsFav(data.username)
                val favoriteIcon = if (isFavorite) {
                    R.drawable.ic_favorite
                } else {
                    R.drawable.ic_favorite_blank
                }
                binding.favorite.setImageDrawable(
                    ContextCompat.getDrawable(applicationContext, favoriteIcon)
                )

                favoriteOnClick(data)
            }
        }
    }

    private fun favoriteOnClick(data: ItemList?){
        if (data != null) {
            val isFavorite = favoriteViewModel.fetchIsFav(data.username)
            binding.favorite.setOnClickListener {
                lifecycleScope.launch(Dispatchers.IO) {
                    if (isFavorite) {
                        favoriteViewModel.deleteUser(data.username)
                        withContext(Dispatchers.Main) {
                            binding.favorite.setImageDrawable(
                                ContextCompat.getDrawable(applicationContext, R.drawable.ic_favorite_blank)
                            )
                            runOnUiThread { Toast.makeText(applicationContext, "${data.username} telah dihapus", Toast.LENGTH_SHORT).show() }
                        }
                    } else {
                        favoriteViewModel.postFav(data)
                        withContext(Dispatchers.Main) {
                            binding.favorite.setImageDrawable(
                                ContextCompat.getDrawable(applicationContext, R.drawable.ic_favorite)
                            )
                            runOnUiThread { Toast.makeText(applicationContext, "${data.username} telah ditambahkan", Toast.LENGTH_SHORT).show() }
                        }
                    }
                }
            }
        }
    }

    private fun initUsers(user: DetailUserResponse){
        Glide.with(this)
            .load(user.avatarUrl)
            .apply(RequestOptions.circleCropTransform())
            .into(binding.imgItemPhoto)
        binding.apply {
            tvUserName.text = user.login
            tvName.text = user.name
            followersDetail.text = resources.getString(R.string.followers_detail, user.followers)
            followingDetail.text = resources.getString(R.string.following_detail, user.following)
        }
    }

}