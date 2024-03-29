package com.foodapps.data.repository

import com.foodapps.data.datasource.Menu.MenuDataSource
import com.foodapps.data.model.Menu


interface MenuRepository {
    fun getMenus(): List<Menu>
}

class MenuRepositoryImpl(private val dataSource: MenuDataSource) : MenuRepository {
    override fun getMenus(): List<Menu> = dataSource.getMenus()
}