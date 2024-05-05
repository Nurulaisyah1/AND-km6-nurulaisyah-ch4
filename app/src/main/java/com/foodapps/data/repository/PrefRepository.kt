package com.foodapps.data.repository

import com.foodapps.data.datasource.pref.PrefDataSource

interface PrefRepository {
    fun isUsingGridMode(): Boolean

    fun setUsingGridMode(isUsingDarkMode: Boolean)
}

class PrefRepositoryImpl(private val dataSource: PrefDataSource) : PrefRepository {
    override fun isUsingGridMode(): Boolean {
        return dataSource.isUsingGridMode()
    }

    override fun setUsingGridMode(isUsingDarkMode: Boolean) {
        return dataSource.setUsingGridMode(isUsingDarkMode)
    }
}
