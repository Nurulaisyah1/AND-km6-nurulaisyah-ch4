package com.foodapps.data.source.local

import android.content.ContentValues.TAG
import android.content.SharedPreferences
import android.util.Log

interface UserPreference {
    fun isUsingGridMode(): Boolean

    fun setUsingGridMode(isUsingGridMode: Boolean)
}

class UserPreferenceImpl(private val pref: SharedPreferences) : UserPreference {
    companion object {
        const val KEY_IS_USING_GRID_MODE = "KEY_IS_USING_GRID_MODE"
        const val PREF_NAME = "FoodApps-pref"// Define the constant here
    }

    override fun isUsingGridMode(): Boolean {
        return pref.getBoolean(KEY_IS_USING_GRID_MODE, false)
    }

    override fun setUsingGridMode(isUsingGridMode: Boolean) {
        pref.edit().putBoolean(KEY_IS_USING_GRID_MODE, isUsingGridMode).apply()
        Log.e(TAG, "Saving value user pref $isUsingGridMode")
    }
}

