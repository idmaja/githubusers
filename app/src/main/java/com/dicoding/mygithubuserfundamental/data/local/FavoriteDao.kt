package com.dicoding.mygithubuserfundamental.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface FavoriteDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertFav(favUser: Favorite)

    @Query("SELECT * from favorite")
    fun getFav(): LiveData<List<Favorite>>

    @Query("DELETE FROM favorite WHERE username = :username")
    fun deleteFav(username: String)

    @Query("SELECT EXISTS(SELECT * FROM favorite WHERE username = :username)")
    fun isFavorite(username: String): Boolean
}