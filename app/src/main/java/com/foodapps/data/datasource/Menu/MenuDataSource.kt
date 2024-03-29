package com.foodapps.data.datasource.Menu

import com.foodapps.data.model.Menu

interface MenuDataSource {
    fun getMenus(): List<Menu>
}