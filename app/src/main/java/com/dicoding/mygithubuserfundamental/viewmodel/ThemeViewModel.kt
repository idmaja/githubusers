package com.dicoding.mygithubuserfundamental.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.dicoding.mygithubuserfundamental.datastore.ThemePreference
import kotlinx.coroutines.launch

class ThemeViewModel(private val preferences: ThemePreference) : ViewModel() {
    fun getTheme(): LiveData<Boolean> {
        return preferences.getTheme().asLiveData()
    }

    fun saveTheme(isThemeActive: Boolean) {
        viewModelScope.launch {
            preferences.saveTheme(isThemeActive)
        }
    }
}