package com.foodapps.data.source.local.database

import AppDatabase
import android.app.Application
import androidx.room.Room
import com.google.firebase.crashlytics.FirebaseCrashlytics

class MyApp : Application() {

    companion object {
        lateinit var database: AppDatabase
            private set
    }

    override fun onCreate() {
        super.onCreate()

        // Initialize Firebase Crashlytics
        FirebaseCrashlytics.getInstance().setCrashlyticsCollectionEnabled(true)

        // Initialize Room database
        database = Room.databaseBuilder(applicationContext, AppDatabase::class.java, "my-database").build()
    }
}

class SomeClass {
    fun someMethod() {
        try {
            // Code that may cause an exception
        } catch (e: Exception) {
            // Log the exception to Firebase Crashlytics
            FirebaseCrashlytics.getInstance().recordException(e)
        }
    }
}
