package com.dicoding.mygithubuserfundamental.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.mygithubuserfundamental.data.response.ItemList
import com.dicoding.mygithubuserfundamental.data.response.Search
import com.dicoding.mygithubuserfundamental.data.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchViewModel : ViewModel() {
    private val _listUser = MutableLiveData<List<ItemList>>()
    val listUser: LiveData<List<ItemList>> = _listUser

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    init {
        findingUser("arif")
    }

    fun findingUser(username: String) {
        _isLoading.value = true

        val client = ApiConfig.getApiService().getSearch(username)
        client.enqueue(object : Callback<Search> {
            override fun onResponse(call: Call<Search>, response: Response<Search>) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _listUser.value = response.body()?.items
                } else {
                    Log.e("MainViewModel", "Error: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<Search>, t: Throwable) {
                _isLoading.value = false
                Log.e("MainViewModel", "Error: ${t.message}")
            }
        })
    }
}