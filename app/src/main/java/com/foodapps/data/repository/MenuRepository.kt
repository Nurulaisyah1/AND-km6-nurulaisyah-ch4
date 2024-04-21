package com.foodapps.data.repository

import com.foodapps.data.api.MenuApi
import com.foodapps.data.datasource.Menu.MenuDataSource
import com.foodapps.data.model.Menu
import com.foodapps.data.network.model.checkout.CheckoutRequestPayload
import com.foodapps.data.network.model.checkout.CheckoutResponse
import com.foodapps.data.network.model.menus.MenuResponse
import com.foodapps.data.source.network.ResponseWrapper
import kotlinx.coroutines.flow.Flow


interface MenuRepository {
    suspend fun getAllMenus(): List<Menu>
    suspend fun addMenu(menu: Menu)
    suspend fun updateMenu(menu: Menu)
    suspend fun deleteMenu(menu: Menu)
    fun getMenuList(onResult: (ResponseWrapper<List<Menu>>) -> Unit)
    suspend fun getMenusByCategory(categorySlug: String): Flow<List<Menu>>

}

class MenuDataSourceImpl(private val menuApi: MenuApi) : MenuDataSource {
    override suspend fun getMenus(categorySlug: String?): MenuResponse {
        TODO("Not yet implemented")
    }

    override suspend fun createOrder(payload: CheckoutRequestPayload): CheckoutResponse {
        TODO("Not yet implemented")
    }

    override suspend fun getMenusByCategory(categorySlug: String): List<Menu> {
        return menuApi.getMenusByCategory(categorySlug)
    }

    suspend fun getAllMenus(): List<Menu> {
        return menuApi.getAllMenus()
    }

    suspend fun addMenu(menu: Menu) {
        menuApi.addMenu(menu)
    }

    suspend fun updateMenu(menu: Menu) {
        menuApi.updateMenu(menu)
    }

    suspend fun deleteMenu(menu: Menu) {
        menuApi.deleteMenu(menu)
    }
}