package com.dicoding.mygithubuserfundamental.viewmodel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.dicoding.mygithubuserfundamental.data.local.Favorite
import com.dicoding.mygithubuserfundamental.data.local.FavoriteRepository
import com.dicoding.mygithubuserfundamental.data.response.ItemList

class FavoriteViewModel (application: Application) : ViewModel() {
    private val favoriteRepo: FavoriteRepository = FavoriteRepository(application)

    fun postFav(itemsItem: ItemList) {
        val favorite = Favorite(itemsItem.username, itemsItem.avatarUrl)
        favoriteRepo.insertFavorite(favorite)
    }

    fun fetchIsFav(username: String): Boolean = favoriteRepo.isFavorite(username)
    fun fetchFavList(): LiveData<List<Favorite>> = favoriteRepo.getFavoriteLists()

    fun deleteUser(username: String) {
        favoriteRepo.removeUser(username)
    }

}