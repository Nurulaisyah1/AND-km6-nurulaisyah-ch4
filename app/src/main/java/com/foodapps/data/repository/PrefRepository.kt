package com.foodapps.data.repository

import com.foodapps.data.datasource.pref.PrefDataSource
import com.foodapps.data.source.local.UserPreference

interface PrefRepository {
    fun isUsingGridMode(): Boolean

    fun setUsingGridMode(isUsingDarkMode: Boolean)
}


class PrefRepositoryImpl(private val userPreference: UserPreference) : PrefDataSource {
    override fun isUsingGridMode(): Boolean {
        return userPreference.isUsingGridMode()
    }

    override fun setUsingGridMode(isUsingDarkMode: Boolean) {
        return userPreference.setUsingGridMode(isUsingDarkMode)
    }

}
