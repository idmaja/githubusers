package com.dicoding.mygithubuserfundamental.data.retrofit

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import com.dicoding.mygithubuserfundamental.data.response.DetailUserResponse
import com.dicoding.mygithubuserfundamental.data.response.ItemList
import com.dicoding.mygithubuserfundamental.data.response.Search

interface ApiService {
    @GET("search/users")
    fun getSearch(@Query("q") username: String): Call<Search>

    @GET("users/{username}")
    fun getDetailUser(@Path("username") username: String?): Call<DetailUserResponse>

    @GET("users/{username}/followers")
    fun getFollowers(@Path("username") username: String?): Call<List<ItemList>>

    @GET("users/{username}/following")
    fun getFollowing(@Path("username") username: String?): Call<List<ItemList>>

}