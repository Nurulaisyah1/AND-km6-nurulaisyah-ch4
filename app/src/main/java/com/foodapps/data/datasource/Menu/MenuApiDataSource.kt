package com.foodapps.data.datasource.menu

import com.foodapps.data.datasource.Menu.MenuDataSource
import com.foodapps.data.network.model.checkout.CheckoutRequestPayload
import com.foodapps.data.network.model.checkout.CheckoutResponse
import com.foodapps.data.network.model.menus.MenuResponse
import com.foodapps.data.network.services.FoodAppApiService

class MenuApiDataSource(private val service: FoodAppApiService) : MenuDataSource {

    override suspend fun getMenus(categorySlug: String?): MenuResponse {
        return service.getMenus(categorySlug)
    }

    override suspend fun createOrder(payload: CheckoutRequestPayload): CheckoutResponse {
        return service.createOrder(payload)
    }
}
