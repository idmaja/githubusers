package com.dicoding.mygithubuserfundamental.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.mygithubuserfundamental.data.response.DetailUserResponse
import com.dicoding.mygithubuserfundamental.data.response.ItemList
import com.dicoding.mygithubuserfundamental.data.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UsersViewModel (private val username: String?) : ViewModel() {
    companion object {
        private const val TEXT = "MainViewModel"
    }

    private val _users = MutableLiveData<DetailUserResponse>()
    val users: LiveData<DetailUserResponse> = _users

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _isLoadingFragment = MutableLiveData<Boolean>()
    val isLoadingFrag: LiveData<Boolean> = _isLoadingFragment

    private val _listFollowers = MutableLiveData<List<ItemList>>()
    val listFollowers: LiveData<List<ItemList>> = _listFollowers

    private val _listFollowing = MutableLiveData<List<ItemList>>()
    val listFollowing: LiveData<List<ItemList>> = _listFollowing

    init {
        fetchUsersData()
    }

    private fun fetchUsersData() {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getDetailUser(username)
        client.enqueue(object : Callback<DetailUserResponse> {
            override fun onResponse(call: Call<DetailUserResponse>, response: Response<DetailUserResponse>) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _users.value = response.body()

//                    FOLLOWERS
                    _isLoadingFragment.value = true
                    val clientFollowersFragment = ApiConfig.getApiService().getFollowers(username)
                    clientFollowersFragment.enqueue(object : Callback<List<ItemList>> {
                        override fun onResponse(call: Call<List<ItemList>>, response: Response<List<ItemList>>) {
                            _isLoadingFragment.value = false
                            if (response.isSuccessful) {
                                _listFollowers.value = response.body()
                            } else {
                                Log.e(TEXT, "Gagal mendapatkan followers: ${response.message()}")
                            }
                        }

                        override fun onFailure(call: Call<List<ItemList>>, t: Throwable) {
                            _isLoadingFragment.value = false
                            Log.e(TEXT, "Gagal mendapatkan followers: ${t.message}")
                        }
                    })

//                    FOLLOWING
                    _isLoadingFragment.value = true
                    val clientFollowingFragment = ApiConfig.getApiService().getFollowing(username)
                    clientFollowingFragment.enqueue(object : Callback<List<ItemList>> {
                        override fun onResponse(call: Call<List<ItemList>>, response: Response<List<ItemList>>) {
                            _isLoadingFragment.value = false
                            if (response.isSuccessful) {
                                _listFollowing.value = response.body()
                            } else {
                                Log.e(TEXT, "Gagal! ${response.message()}")
                            }
                        }

                        override fun onFailure(call: Call<List<ItemList>>, t: Throwable) {
                            _isLoadingFragment.value = false
                            Log.e(TEXT, "Gagal! ${t.message}")
                        }

                    })
                } else {
                    Log.e(TEXT, "Gagal! ${response.message()}")
                }
            }

            override fun onFailure(call: Call<DetailUserResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TEXT, "Gagal! ${t.message}")
            }
        })
    }
}