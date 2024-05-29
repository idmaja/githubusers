package com.dicoding.mygithubuserfundamental.ui

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.SearchView
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.mygithubuserfundamental.R
import com.dicoding.mygithubuserfundamental.adapter.UsersAdapter
import com.dicoding.mygithubuserfundamental.data.response.ItemList
import com.dicoding.mygithubuserfundamental.databinding.ActivityMainBinding
import com.dicoding.mygithubuserfundamental.datastore.ThemePreference
import com.dicoding.mygithubuserfundamental.viewmodel.SearchViewModel
import com.dicoding.mygithubuserfundamental.viewmodel.ThemeViewModel
import com.dicoding.mygithubuserfundamental.viewmodel.ThemeViewModelFactory

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val searchUsers by viewModels<SearchViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
//        ACTION BAR
        supportActionBar?.setDisplayShowHomeEnabled(true)

//        SEARCH
        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = binding.searchUser

        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.queryHint = getString(R.string.searchbar_hint)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                searchUsers.findingUser(query)
                searchView.clearFocus()
                binding.tvNoSearch.visibility = View.GONE
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                if (newText.isEmpty()) {
                    binding.tvNoSearch.visibility = View.GONE
                } else {
                    binding.tvNoSearch.visibility = View.GONE
                }
                return false
            }
        })

        searchUsers.listUser.observe(this) {
            listUser -> val linearLayoutManager = LinearLayoutManager(this)
            binding.listUsers.layoutManager = linearLayoutManager
            fetchUsers(listUser)
        }

        searchUsers.isLoading.observe(this) {
            isLoading -> showLoading(isLoading)
        }

//        THEMES
        val preferences = ThemePreference.getInstance(dataStore)
        val themes = ViewModelProvider(this,
            ThemeViewModelFactory(preferences))[ThemeViewModel::class.java]
            themes.getTheme().observe(this) {
            darkModeIsActive -> theme(darkModeIsActive)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.actionbar_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_favorite -> {
                startActivity(Intent(this, FavoriteUserActivity::class.java))
                true
            }
            R.id.menu_setting -> {
                startActivity(Intent(this, SettingActivity::class.java))
                true
            }
            else -> true
        }
    }

    private fun fetchUsers(usersData: List<ItemList>) {
        val usersAdapter = UsersAdapter(usersData)
        if (usersAdapter.itemCount == 0) binding.tvNoSearch.visibility = View.VISIBLE
        binding.listUsers.adapter = usersAdapter
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) {
            View.VISIBLE
        } else {
            View.GONE
        }
        binding.listUsers.visibility = if (!isLoading) {
            View.VISIBLE
        } else {
            View.GONE
        }
    }

    private fun theme(darkThemeIsActive: Boolean) {
        val darkMode = if (darkThemeIsActive) AppCompatDelegate.MODE_NIGHT_YES else AppCompatDelegate.MODE_NIGHT_NO
        AppCompatDelegate.setDefaultNightMode(darkMode)
    }
}

//    private fun initMainActivity() {
//        binding = ActivityMainBinding.inflate(layoutInflater)
//        setContentView(binding.root)
//        supportActionBar?.setDisplayShowHomeEnabled(true)
//        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
//        val searchView = binding.searchUser
//
//        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
//        searchView.queryHint = getString(R.string.searchbar_hint)
//        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
//            override fun onQueryTextSubmit(query: String): Boolean {
//                searchUsers.findingUser(query)
//                searchView.clearFocus()
//                binding.tvNoSearch.visibility = View.GONE
//                return true
//            }
//
//            override fun onQueryTextChange(newText: String): Boolean {
//                return false
//            }
//        })
//    }
//
//
//    private fun setupSearchView() {
//        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
//        val searchView = binding.searchUser
//
//        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
//        searchView.queryHint = getString(R.string.searchbar_hint)
//        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
//            override fun onQueryTextSubmit(query: String): Boolean {
//                searchUsers.findingUser(query)
//                searchView.clearFocus()
//                binding.tvNoSearch.visibility = View.GONE
//                return true
//            }
//
//            override fun onQueryTextChange(newText: String): Boolean {
//                return false
//            }
//        })
//    }
