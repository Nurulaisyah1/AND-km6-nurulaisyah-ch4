package com.foodapps.data.mapper

import com.foodapps.data.model.Cart
import com.foodapps.data.source.local.database.entity.CartEntity

object CartMapper {
    fun toCart(entity: CartEntity): Cart {
        return Cart(
            entity.id,
            entity.menuId,
            entity.menuName,
            entity.menuImgUrl,
            entity.menuPrice,
            entity.itemQuantity,
            entity.itemNotes
        )
    }

    fun toCartEntity(cart: Cart): CartEntity {
        return CartEntity(
            cart.id,
            cart.menuId,
            cart.menuName,
            cart.menuImgUrl,
            cart.menuPrice,
            cart.itemQuantity,
            cart.itemNotes
        )
    }
}
