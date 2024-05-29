package com.dicoding.mygithubuserfundamental.data.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Favorite(
    @PrimaryKey
    @ColumnInfo(name = "username")
    var username: String,

    @ColumnInfo(name = "avatar")
    var avatar: String
)
