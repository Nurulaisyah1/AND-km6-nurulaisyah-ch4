package com.foodapps.data.source.local

import android.content.Context
import com.foodapps.utils.SharedPreferenceUtils

interface UserPreference {
    fun isUsingGridMode(): Boolean

    fun setUsingGridMode(isUsingDarkMode: Boolean)
}

class UserPreferenceImpl(private val context: Context) : UserPreference {
    private val pref = SharedPreferenceUtils.createPreference(context, PREF_NAME)

    override fun isUsingGridMode(): Boolean = pref.getBoolean(KEY_IS_USING_GRID_MODE, false)

    override fun setUsingGridMode(isUsingDarkMode: Boolean) {
        pref.edit().putBoolean(KEY_IS_USING_GRID_MODE, isUsingDarkMode).apply()
    }

    companion object {
        const val PREF_NAME = "FoodApps-pref"
        const val KEY_IS_USING_GRID_MODE = "KEY_IS_USING_GRID_MODE"
    }
}
