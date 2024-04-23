package com.foodapps.presentation.main

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.foodapps.R
import com.foodapps.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.crashlytics.FirebaseCrashlytics

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Enable edge-to-edge display
        enableEdgeToEdge()

        // Set up content insets controller
        WindowCompat.setDecorFitsSystemWindows(window, false)

        // Initialize Firebase Authentication
        auth = FirebaseAuth.getInstance()

        // Inflate layout using view binding
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize Firebase Crashlytics
        FirebaseCrashlytics.getInstance().setCrashlyticsCollectionEnabled(true)

        // Check if the user is already logged in
        if (userIsLoggedIn()) {
            // If logged in, navigate to home fragment
            navigateToHomeFragment()
        } else {
            // If not logged in, proceed with normal setup
            // Set up bottom navigation with NavController
            setupBottomNav()
        }
    }

    private fun userIsLoggedIn(): Boolean {
        // Check if the current user is authenticated
        return auth.currentUser != null
    }

    private fun navigateToHomeFragment() {
        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        navController.navigate(R.id.menu_tab_home)
    }

    private fun setupBottomNav() {
        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        binding.navView.setupWithNavController(navController)
    }
}
