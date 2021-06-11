package com.mjandroiddev.nykaalibrary.ui

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import com.mjandroiddev.nykaalibrary.R
import com.mjandroiddev.nykaalibrary.databinding.ActivityMainBinding
import com.mjandroiddev.nykaalibrary.utils.log
import com.mjandroiddev.nykaalibrary.viewmodels.SessionViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit

/*
* MainActivity host the navigation Controller & configure the actionbar
*
* Created by Manoj Singh Rawal (mjAndroidDev@gmail.com) on 2021-06-11.
* */
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val sessionViewModel: SessionViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val navHomeFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        NavigationUI.setupActionBarWithNavController(
            this, navHomeFragment.navController,
            AppBarConfiguration(
                mutableSetOf(
                    R.id.homeFragment
                )
            )
        )
    }
}