package com.dicoding.mygithubuserfundamental.datastore

import android.content.res.Resources.Theme
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ThemePreference private constructor(private val dataStore: DataStore<Preferences>) {
    private val Theme = booleanPreferencesKey("theme")

    fun getTheme(): Flow<Boolean> {
        return dataStore.data.map {
                preferences -> preferences[Theme] ?: false
        }
    }

    suspend fun saveTheme(isDarkActive: Boolean) {
        dataStore.edit {
                preferences -> preferences[Theme] = isDarkActive
        }
    }

    companion object {
        @Volatile
        private var instance: ThemePreference? = null

        fun getInstance(dataStore: DataStore<Preferences>): ThemePreference {
            return instance ?: synchronized(this) {
                val darkModePreferencesInstance = ThemePreference(dataStore)
                instance = darkModePreferencesInstance
                darkModePreferencesInstance
            }
        }
    }
}