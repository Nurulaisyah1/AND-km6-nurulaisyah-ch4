package com.foodapps.utils

import android.content.Context
import android.content.SharedPreferences

object Preferences {
    private const val PREFS_NAME = "MyPrefs"
    private const val BOOLEAN_KEY = "isLogged"

    fun setLoggedStatus(
        context: Context,
        isLogged: Boolean,
    ) {
        val sharedPref: SharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        with(sharedPref.edit()) {
            putBoolean(BOOLEAN_KEY, isLogged)
            apply()
        }
    }

    fun getLoggedStatus(context: Context): Boolean {
        val sharedPref: SharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        return sharedPref.getBoolean(BOOLEAN_KEY, false)
    }
}
