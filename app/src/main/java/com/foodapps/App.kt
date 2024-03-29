package com.foodapps

import android.app.Application
import com.foodapps.data.local.database.AppDatabase

class App : Application(){
    override fun onCreate() {
        super.onCreate()
        AppDatabase.getInstance(this)
    }
}