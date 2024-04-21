package com.foodapps.data.source.local.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.foodapps.data.source.local.database.entity.CartEntity
import com.foodapps.data.source.local.database.entity.EntityCartItem
import kotlinx.coroutines.flow.Flow

@Dao
interface CartDao {
    @Query("SELECT * FROM carts WHERE id = :cartId")
    fun getCartById(cartId: String): Flow<CartEntity>

    @Query("SELECT * FROM carts")
    fun getAllCarts(): Flow<List<CartEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCart(cart: CartEntity): Long

    @Update
    suspend fun updateCart(cart: CartEntity): Int

    @Query("DELETE FROM carts WHERE id = :cartId")
    suspend fun deleteCart(cartId: Int): Int

    @Query("DELETE FROM carts")
    suspend fun clearCart()

    @Query("SELECT * FROM cart_items")
    suspend fun getCartItems(): List<EntityCartItem>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCartItem(cartItem: EntityCartItem)

    @Query("DELETE FROM cart_items WHERE id = :itemId")
    suspend fun deleteCartItem(itemId: String)

}
