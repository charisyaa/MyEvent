package com.example.myevent.ui

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.navigation.findNavController
import androidx.navigation.navOptions
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.myevent.R
import com.example.myevent.databinding.ActivityMainBinding
import com.example.myevent.ui.setting.SettingViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private val settingViewModel: SettingViewModel by viewModels {
        ViewModelFactory.getInstance(this.application)
    }


    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Observe theme setting and apply the correct theme mode
        settingViewModel.getThemeSettings().observe(this) { isDarkModeActive ->
            if (isDarkModeActive) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }

        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_favorite, R.id.navigation_setting
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)


        navView.setOnNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.navigation_home -> {
                    // Navigasi ke HomeFragment dan bersihkan back stack
                    navController.navigate(R.id.navigation_home, null, navOptions {
                        popUpTo(R.id.mobile_navigation) { inclusive = true }
                    })
                    true
                }
                R.id.navigation_dashboard -> {
                    // Navigasi ke DashboardFragment dan bersihkan back stack
                    navController.navigate(R.id.navigation_dashboard, null, navOptions {
                        popUpTo(R.id.mobile_navigation) { inclusive = true }
                    })
                    true
                }
                R.id.navigation_favorite -> {
                    // Navigasi ke FavoriteFragment dan bersihkan back stack
                    navController.navigate(R.id.navigation_favorite, null, navOptions {
                        popUpTo(R.id.mobile_navigation) { inclusive = true }
                    })
                    true
                }
                R.id.navigation_setting -> {
                    // Navigasi ke FavoriteFragment dan bersihkan back stack
                    navController.navigate(R.id.navigation_setting, null, navOptions {
                        popUpTo(R.id.mobile_navigation) { inclusive = true }
                    })
                    true
                }
                else -> false
            }
        }
    }
}