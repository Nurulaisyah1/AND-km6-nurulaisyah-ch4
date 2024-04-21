package com.foodapps.data.datasource.cart

import com.foodapps.data.model.Cart
import com.foodapps.data.source.local.database.entity.CartEntity
import com.foodapps.data.source.local.database.dao.CartDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

// CartDataSource interface
interface CartDataSource {
    suspend fun deleteCart(cartId: Int): Int
    suspend fun updateCart(cart: CartEntity): Int
    fun getAllCarts(): Flow<List<Cart>>
    suspend fun insertCart(cart: CartEntity): Long
    suspend fun clearCart() // Removed return type as it's not required
}

// Implementasi dari antarmuka CartDataSource
class CartDataSourceImpl(private val cartDao: CartDao) : CartDataSource {

    override suspend fun deleteCart(cartId: Int): Int {
        return cartDao.deleteCart(cartId)
    }

    override suspend fun updateCart(cart: CartEntity): Int {
        return cartDao.updateCart(cart)
    }

    override fun getAllCarts(): Flow<List<Cart>> {
        return cartDao.getAllCarts().map { cartEntities ->
            cartEntities.map { cartEntity ->
                // Converting CartEntity to Cart before returning
                Cart(
                    cartEntity.id,
                    cartEntity.menuId,
                    cartEntity.menuName,
                    cartEntity.menuImgUrl,
                    cartEntity.menuPrice,
                    cartEntity.itemQuantity,
                    cartEntity.itemNotes
                )
            }
        }
    }

    override suspend fun insertCart(cart: CartEntity): Long {
        return cartDao.insertCart(cart)
    }

    override suspend fun clearCart() {
        cartDao.clearCart()
    }
}
