package com.foodapps.data.datasource.menu

import com.foodapps.data.model.Cart
import com.foodapps.data.network.model.CheckoutResponse
import com.foodapps.data.network.model.menus.MenuResponse

interface MenuDataSource {
    suspend fun getMenus(categorySlug: String?): MenuResponse

    suspend fun createOrder(username: String, cart: List<Cart>, totalPrice: Double): CheckoutResponse

    suspend fun getMenuDataSource(params: Map<String, String>): MenuResponse

}
