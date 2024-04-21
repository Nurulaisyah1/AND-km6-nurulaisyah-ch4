package com.foodapps.presentation.checkout

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.foodapps.data.repository.CartRepository
import com.foodapps.utils.ResultWrapper
import kotlinx.coroutines.launch

class CheckoutViewModel(private val cartRepository: CartRepository) : ViewModel() {

    // LiveData untuk hasil checkout
    private val _checkoutResult = MutableLiveData<ResultWrapper<Boolean>>()
    val checkoutResult: LiveData<ResultWrapper<Boolean>>
        get() = _checkoutResult

    // Menghapus semua item di keranjang saat checkout
    fun checkout() {
        viewModelScope.launch {
            try {
                // Lakukan proses checkout di repository
                val result = cartRepository.checkout()
                // Setel hasil checkout ke LiveData
                _checkoutResult.postValue(ResultWrapper.Success(result))
            } catch (e: Exception) {
                // Jika terjadi kesalahan, setel hasil error ke LiveData
                _checkoutResult.postValue(ResultWrapper.Error(e))
            }
        }
    }

    // Fungsi untuk menghapus semua item di keranjang
    fun clearCart() {
        viewModelScope.launch {
            cartRepository.deleteAllCarts()
        }
    }
}

