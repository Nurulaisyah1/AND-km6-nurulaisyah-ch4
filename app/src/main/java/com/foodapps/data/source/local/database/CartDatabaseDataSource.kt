package com.foodapps.data.source.local.database

import android.content.Context
import androidx.room.Room
import com.foodapps.data.datasource.cart.CartDataSource
import com.foodapps.data.model.Cart
import com.foodapps.data.source.local.database.dao.CartDao
import com.foodapps.data.source.local.database.entity.CartEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class CartDatabaseDataSource(private val cartDao: CartDao) : CartDataSource {

    override suspend fun deleteCart(cartId: Int): Int {
        return cartDao.deleteCart(cartId)
    }

    override suspend fun updateCart(cart: CartEntity): Int {
        return cartDao.updateCart(cart)
    }

    override fun getAllCarts(): Flow<List<Cart>> {
        return cartDao.getAllCarts().map { cartEntities ->
            cartEntities.map { cartEntity ->
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

    companion object {
        @Volatile private var instance: CartDatabase? = null

        fun getInstance(context: Context): CartDatabase {
            return instance ?: synchronized(this) {
                val newInstance = Room.databaseBuilder(
                    context.applicationContext,
                    CartDatabase::class.java,
                    "cart_database"
                ).build()
                instance = newInstance
                newInstance
            }
        }
    }
}
