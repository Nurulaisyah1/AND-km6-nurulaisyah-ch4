package com.foodapps.data.mapper

import com.foodapps.data.local.database.entity.CartEntity
import com.foodapps.data.model.Cart


fun Cart?.toCartEntity() = CartEntity(
    id = this?.id,
    menuId = this?.menuId ?:  "",
    itemQuantity = this?.itemQuantity ?: 0,
    menuName = this?.menuName.orEmpty(),
    menuPrice = this ?.menuPrice ?: 0.0,
    menuImgUrl = this?.menuImgUrl.orEmpty()

)
fun CartEntity?.toCart() = Cart (
    id = this?.id,
    menuId =this?.menuId ?:  "",
    itemQuantity = this?.itemQuantity ?: 0,
    menuName = this?.menuName.orEmpty(),
    menuPrice = this ?.menuPrice ?: 0.0,
    menuImgUrl = this?.menuImgUrl.orEmpty()
)

fun List<CartEntity>.toCartList() = this.map{ it.toCart() }