package com.foodapps.data.repository

import com.foodapps.data.mapper.CartMapper
import com.foodapps.data.model.Cart
import com.foodapps.data.model.Menu
import com.foodapps.data.source.local.database.dao.CartDao
import com.foodapps.data.source.local.database.entity.CartEntity
import com.foodapps.utils.ResultWrapper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

interface CartRepository {
    fun getAllCarts(): Flow<List<Cart>>
    fun getCartById(cartId: Int): Flow<Cart?>
    suspend fun insertCart(cart: Cart)
    suspend fun updateCart(cart: Cart)
    suspend fun deleteCart(cart: Cart)
    suspend fun deleteAllCarts()
    suspend fun decreaseCart(item: Cart)
    fun increaseCart(item: Cart)
    fun setCartNotes(item: Cart)
    suspend fun createCart(menu: Menu, quantity: Int): ResultWrapper<Boolean>
    suspend fun checkout(): Boolean
}

class CartRepositoryImpl(private val cartDao: CartDao) : CartRepository {

    override fun getCartById(cartId: Int): Flow<Cart?> {
        return cartDao.getCartById(cartId.toString()).map { cartEntity: CartEntity? ->
            cartEntity?.let { CartMapper.toCart(it) }
        }
    }
    override suspend fun checkout(): Boolean {
        return withContext(Dispatchers.IO) {
            // Perform checkout process here
            try {
                true
            } catch (e: Exception) {
                false
            }
        }
    }
    override fun getAllCarts(): Flow<List<Cart>> {
        return cartDao.getAllCarts().map { cartEntities: List<CartEntity> ->
            cartEntities.map { CartMapper.toCart(it) }
        }
    }

    override suspend fun insertCart(cart: Cart) {
        cartDao.insertCart(CartMapper.toCartEntity(cart))
    }

    override suspend fun updateCart(cart: Cart) {
        cartDao.updateCart(CartMapper.toCartEntity(cart))
    }

    override suspend fun deleteCart(cart: Cart) {
        cart.id?.let { nonNullId ->
            cartDao.deleteCart(nonNullId)
        }
    }

    override suspend fun deleteAllCarts() {
        cartDao.clearCart()
    }

    override suspend fun decreaseCart(item: Cart) {
        val cartEntity = CartMapper.toCartEntity(item)
        if (cartEntity.itemQuantity > 1) {
            cartEntity.itemQuantity -= 1
            cartDao.updateCart(cartEntity)
        } else {
            // Ensure cartEntity.id is not null before deleting
            cartEntity.id?.let { nonNullId ->
                cartDao.deleteCart(nonNullId)
            }
        }
    }


    override fun increaseCart(item: Cart) {
        val cartEntity = CartMapper.toCartEntity(item)

        // Increment item quantity
        cartEntity.itemQuantity += 1

        // Launch a coroutine to execute the suspend function
        CoroutineScope(Dispatchers.IO).launch {
            cartDao.updateCart(cartEntity)
        }
    }

    override fun setCartNotes(item: Cart) {
        val cartEntity = CartMapper.toCartEntity(item)
        CoroutineScope(Dispatchers.IO).launch {
            cartDao.updateCart(cartEntity)
        }
    }

        override suspend fun createCart(menu: Menu, quantity: Int): ResultWrapper<Boolean> {
            return suspendCoroutine { continuation ->
                CoroutineScope(Dispatchers.IO).launch {
                    val existingCart = cartDao.getCartById(menu.id)
                    if (existingCart == null) {
                        val cartEntity = CartEntity(
                            menuId = menu.id,
                            menuName = menu.name,
                            menuImgUrl = menu.imgUrl,
                            menuPrice = menu.price,
                            itemQuantity = quantity,
                            itemNotes = ""
                        )
                        cartDao.insertCart(cartEntity)
                        continuation.resume(ResultWrapper.Success(true))
                    } else {
                        val error = Exception("Item already exists in the cart.")
                        continuation.resumeWithException(error)                    }
                }
            }
        }
}