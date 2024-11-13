package com.example.myevent.ui.setting

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.mydatastore.SettingPreferences
import com.example.mydatastore.dataStore
import kotlinx.coroutines.launch

class SettingViewModel(application: Application) : ViewModel()  {

    // Inisialisasi SettingPreferences dengan dataStore dari application context
    private val pref: SettingPreferences = SettingPreferences.getInstance(application.applicationContext.dataStore)

    fun getThemeSettings(): LiveData<Boolean> {
        return pref.getThemeSetting().asLiveData()
    }

    fun saveThemeSetting(isDarkModeActive: Boolean) {
        viewModelScope.launch {
            pref.saveThemeSetting(isDarkModeActive)
        }
    }
}