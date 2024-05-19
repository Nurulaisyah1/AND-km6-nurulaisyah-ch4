package com.foodapps.presentation.checkout

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.foodapps.data.repository.CartRepository
import com.foodapps.data.repository.MenuRepository
import com.foodapps.data.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CheckoutViewModel(
    private val cartRepository: CartRepository,
    private val userRepository: UserRepository,
    private val menuRepository: MenuRepository

) : ViewModel() {
    val checkoutData = cartRepository.getCheckoutData().asLiveData(Dispatchers.IO)

    fun checkoutCart() =
        menuRepository.createOrder(
            checkoutData.value?.payload?.first.orEmpty(),
        ).asLiveData(Dispatchers.IO)

    fun deleteAllCart() {
        viewModelScope.launch {
            cartRepository.deleteAllCart().collect {}
        }
    }
    fun isLoggedIn(): Boolean {
        return userRepository.isLoggedIn()
    }
}
