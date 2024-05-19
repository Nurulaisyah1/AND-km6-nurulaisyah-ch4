package com.foodapps.data.datasource.pref

import com.foodapps.data.source.local.UserPreference

interface PrefDataSource {
    fun isUsingGridMode(): Boolean

    fun setUsingGridMode(isUsingDarkMode: Boolean)
}

class PrefDataSourceImpl(private val userPreference: UserPreference) : PrefDataSource {
    override fun isUsingGridMode(): Boolean {
        return userPreference.isUsingGridMode()
    }

    override fun setUsingGridMode(isUsingDarkMode: Boolean) {
        return userPreference.setUsingGridMode(isUsingDarkMode)
    }
}