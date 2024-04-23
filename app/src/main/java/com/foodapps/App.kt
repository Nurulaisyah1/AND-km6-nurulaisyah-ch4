package com.foodapps

import android.app.Application
import android.util.Log
import com.foodapps.data.source.local.database.AppDatabase
import com.google.firebase.FirebaseApp
import com.google.firebase.crashlytics.FirebaseCrashlytics

class App : Application() {
    override fun onCreate() {
        super.onCreate()

        // Initialize AppDatabase
        AppDatabase.getInstance(this)

        // Initialize FirebaseApp
        FirebaseApp.initializeApp(this)

        // Enable Crashlytics
        FirebaseCrashlytics.getInstance().setCrashlyticsCollectionEnabled(true)
    }
}
