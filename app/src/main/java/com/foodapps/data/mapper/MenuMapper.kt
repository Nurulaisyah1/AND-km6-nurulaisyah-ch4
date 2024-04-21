package com.foodapps.data.mapper

import com.foodapps.data.model.Menu
import com.foodapps.data.source.network.model.menus.MenuItemResponse


fun MenuItemResponse?.toProduct() =
    Menu(
        id = this?.id.orEmpty(),
        name = this?.name.orEmpty(),
        price = this?.price ?: 0.0,
        imgUrl = this?.imgUrl.orEmpty(),
        description = this?.description.orEmpty()
    )

fun Collection<MenuItemResponse>?.toProducts() = this?.map {
    it.toProduct()
} ?: listOf()