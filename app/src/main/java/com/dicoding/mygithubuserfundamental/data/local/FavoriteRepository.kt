package com.dicoding.mygithubuserfundamental.data.local

import android.app.Application
import androidx.lifecycle.LiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class FavoriteRepository(application: Application) {
    private val favoriteRoomDao: FavoriteDao
    private val ioScope = GlobalScope

    init {
        val favoriteRoomDatabase = FavoriteDatabase.getInstance(application)
        favoriteRoomDao = favoriteRoomDatabase.favoriteDao()
    }

    fun insertFavorite(favoriteRoom: Favorite) {
        ioScope.launch(Dispatchers.IO) {
            favoriteRoomDao.insertFav(favoriteRoom)
        }
    }

    fun getFavoriteLists(): LiveData<List<Favorite>> = favoriteRoomDao.getFav()

    fun removeUser(username: String) {
        ioScope.launch(Dispatchers.IO) {
            favoriteRoomDao.deleteFav(username)
        }
    }

    fun isFavorite(username: String): Boolean = favoriteRoomDao.isFavorite(username)
}