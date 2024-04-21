package com.foodapps.presentation.cart

import androidx.lifecycle.ViewModel
import com.foodapps.data.model.Cart
import com.foodapps.data.repository.CartRepository
import com.foodapps.utils.ResultWrapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import java.lang.Exception

class CartViewModel(private val cartRepository: CartRepository) : ViewModel() {

    fun getAllCarts(): Flow<ResultWrapper<List<Cart>>> {
        return cartRepository.getAllCarts().map { carts ->
            if (carts.isNotEmpty()) {
                ResultWrapper.Success(carts)
            } else {
                ResultWrapper.Empty()
            }
        }.catch { e ->
            emit(ResultWrapper.Error(exception = Exception(e)))
        }.onStart {
            emit(ResultWrapper.Loading())
        }
    }

    fun decreaseCart(item: Cart): Flow<ResultWrapper<Unit>> {
        return flow {
            emit(ResultWrapper.Loading())
            cartRepository.decreaseCart(item)
            emit(ResultWrapper.Success(Unit))
        }.catch { e ->
            emit(ResultWrapper.Error(exception = Exception(e)))
        }
    }

    fun increaseCart(item: Cart): Flow<ResultWrapper<Unit>> {
        return flow {
            emit(ResultWrapper.Loading())
            cartRepository.increaseCart(item)
            emit(ResultWrapper.Success(Unit))
        }.catch { e ->
            emit(ResultWrapper.Error(exception = Exception(e)))
        }
    }

    fun removeCart(item: Cart): Flow<ResultWrapper<Unit>> {
        return flow {
            emit(ResultWrapper.Loading())
            cartRepository.deleteCart(item)
            emit(ResultWrapper.Success(Unit))
        }.catch { e ->
            emit(ResultWrapper.Error(exception = Exception(e)))
        }
    }

    fun setCartNotes(item: Cart): Flow<ResultWrapper<Unit>> {
        return flow {
            emit(ResultWrapper.Loading())
            cartRepository.setCartNotes(item)
            emit(ResultWrapper.Success(Unit))
        }.catch { e ->
            emit(ResultWrapper.Error(exception = Exception(e)))
        }
    }

    fun isUserLoggedIn(): Boolean {
        return true // Placeholder implementation
    }
}
