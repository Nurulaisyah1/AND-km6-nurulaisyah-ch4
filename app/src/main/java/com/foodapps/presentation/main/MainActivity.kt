package com.foodapps.presentation.main

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.foodapps.R
import com.foodapps.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var auth: FirebaseAuth


    private val mainViewModel: MainViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Enable edge-to-edge display
        enableEdgeToEdge()

        // Set up content insets controller
        WindowCompat.setDecorFitsSystemWindows(window, false)

        // Initialize Firebase Authentication
        auth = FirebaseAuth.getInstance()

        // Initialize Firebase Crashlytics
        // FirebaseCrashlytics.getInstance().setCrashlyticsCollectionEnabled(true)

        // Check if the user is already logged in
        if (!userIsLoggedIn()) {
            // If not logged in, navigate to home fragment
            navigateToHomeFragment()
        } else {
            // If logged in, setup bottom navigation
            setupBottomNav()
        }
    }

    private fun userIsLoggedIn(): Boolean {
        // Check if the current user is authenticated
        return auth.currentUser != null
    }

    private fun navigateToHomeFragment() {
        // Navigate to the home fragment using NavController
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment_activity_main) as NavHostFragment


        val navController = navHostFragment.navController
        navController.navigate(R.id.menu_tab_home)
    }

    private fun setupBottomNav() {
        // Set up bottom navigation using Navigation Component
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment_activity_main) as NavHostFragment


        val navController = navHostFragment.navController
        binding.navView.setupWithNavController(navController)
    }
}
