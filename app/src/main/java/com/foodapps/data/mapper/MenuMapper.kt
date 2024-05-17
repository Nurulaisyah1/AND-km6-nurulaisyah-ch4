package com.foodapps.data.mapper

import com.foodapps.data.model.Menu
import com.foodapps.data.source.network.model.menus.MenuItemResponse

fun MenuItemResponse?.toMenus() =
    Menu(
        id = this?.id.orEmpty(),
        name = this?.name.orEmpty(),
        price = this?.price ?: 0.0,
        imgUrl = this?.imgUrl.orEmpty(),
        detail = this?.description.orEmpty(),
        address = this?.address.orEmpty(),
    )

fun Collection<MenuItemResponse>?.toMenus() =
    this?.map {
        it.toMenus()
    } ?: listOf()
