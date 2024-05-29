package com.dicoding.mygithubuserfundamental.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dicoding.mygithubuserfundamental.datastore.ThemePreference

class ThemeViewModelFactory(private val preference: ThemePreference) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ThemeViewModel::class.java)) {
            return ThemeViewModel(preference) as T
        }
        throw IllegalArgumentException("Tidak menemukan viewmodel")
    }
}