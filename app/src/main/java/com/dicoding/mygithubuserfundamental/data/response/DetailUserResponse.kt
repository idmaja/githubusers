package com.dicoding.mygithubuserfundamental.data.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

data class DetailUserResponse(
    @field:SerializedName("following_url")
    val followingUrl: String,

    @field:SerializedName("login")
    val login: String,

    @field:SerializedName("followers_url")
    val followersUrl: String,

    @field:SerializedName("followers")
    val followers: Int,

    @field:SerializedName("avatar_url")
    val avatarUrl: String,

    @field:SerializedName("following")
    val following: Int,

    @field:SerializedName("name")
    val name: String,
)

@Parcelize
data class ItemList(
    @field:SerializedName("login")
    val username: String,

    @field:SerializedName("avatar_url")
    val avatarUrl: String
): Parcelable

data class Search(
    @field:SerializedName("total_count")
    val totalCount: Int,

    @field:SerializedName("items")
    val items: List<ItemList>
)
