package com.foodapps.data.datasource.pref

import com.foodapps.data.source.local.UserPreference

interface PrefDataSource {


    fun isUsingGridMode(): Boolean

    fun setUsingGridMode(isUsingDarkMode: Boolean)
}

