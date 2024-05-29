package com.dicoding.mygithubuserfundamental.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Favorite::class], version = 1, exportSchema = false)
abstract class FavoriteDatabase : RoomDatabase() {
    companion object {
        @Volatile
        private var instance: FavoriteDatabase? = null

        @JvmStatic
        fun getInstance(context: Context): FavoriteDatabase {
            return instance ?: synchronized(this) {
                instance ?: buildRoomDatabase(context)
                    .also {
                        instance = it
                    }
            }
        }

        private fun buildRoomDatabase(context: Context): FavoriteDatabase {
            return Room
                .databaseBuilder(context.applicationContext,
                    FavoriteDatabase::class.java,
                    "favoriteDatabase")
                .fallbackToDestructiveMigration()
                .build()
        }
    }
    abstract fun favoriteDao(): FavoriteDao
}