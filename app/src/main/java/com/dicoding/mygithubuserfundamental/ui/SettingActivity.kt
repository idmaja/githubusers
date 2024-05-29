package com.dicoding.mygithubuserfundamental.ui

import android.content.Context
import android.os.Bundle
import android.widget.CompoundButton
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.dicoding.mygithubuserfundamental.databinding.ActivitySettingBinding
import com.dicoding.mygithubuserfundamental.datastore.ThemePreference
import com.dicoding.mygithubuserfundamental.viewmodel.ThemeViewModel
import com.dicoding.mygithubuserfundamental.viewmodel.ThemeViewModelFactory

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class SettingActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySettingBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initializeSetting()

        themePreference()
        switchMode()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun themePreference (){
        val preference = ThemePreference.getInstance(dataStore)
        val themeViewModel = ViewModelProvider(this, ThemeViewModelFactory(preference))[ThemeViewModel::class.java]

        themeViewModel.getTheme().observe(this
        ) { isDarkThemeActive: Boolean ->
            if (isDarkThemeActive) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                binding.switchMode.isChecked = true
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                binding.switchMode.isChecked = false
            }
        }
    }

    private fun switchMode (){
        val preference = ThemePreference.getInstance(dataStore)
        val themeViewModel = ViewModelProvider(this, ThemeViewModelFactory(preference))[ThemeViewModel::class.java]

        binding.switchMode.setOnCheckedChangeListener { _: CompoundButton?, isChecked: Boolean ->
            themeViewModel.saveTheme(isChecked)
        }
    }

    private fun initializeSetting(){
        binding = ActivitySettingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Settings"
    }
}