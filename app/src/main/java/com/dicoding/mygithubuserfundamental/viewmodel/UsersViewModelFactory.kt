package com.dicoding.mygithubuserfundamental.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class UsersViewModelFactory (private var name: String?) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(UsersViewModel::class.java)) {
            return UsersViewModel(name) as T
        }
        throw IllegalArgumentException("Error, tidak ditemukan")
    }
}