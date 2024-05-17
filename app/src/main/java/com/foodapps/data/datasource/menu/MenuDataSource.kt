package com.foodapps.data.datasource.menu

import com.foodapps.data.network.model.checkout.CheckoutRequestPayload
import com.foodapps.data.network.model.checkout.CheckoutResponse
import com.foodapps.data.network.model.menus.MenuResponse

interface MenuDataSource {
    suspend fun getMenus(categorySlug: String?): MenuResponse

    suspend fun createOrder(payload: CheckoutRequestPayload): CheckoutResponse
}
