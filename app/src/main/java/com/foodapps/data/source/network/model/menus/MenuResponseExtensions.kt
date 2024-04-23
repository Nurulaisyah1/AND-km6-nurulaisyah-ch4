package com.foodapps.data.source.network.model.menus

import com.foodapps.data.model.Menu
import com.foodapps.data.network.model.menus.MenuResponse

fun MenuResponse.toMenus(): List<Menu> {
    return this.data?.map { MenuItemResponse ->
        Menu(
            id = MenuItemResponse.id.orEmpty(),
            name = MenuItemResponse.name.orEmpty(),
            imgUrl = MenuItemResponse.imgUrl.orEmpty(),
            price = MenuItemResponse.price ?: 0.0,
            detail = MenuItemResponse.description.orEmpty(),
            address = MenuItemResponse.address.orEmpty()
        )
    } ?: emptyList()
}
