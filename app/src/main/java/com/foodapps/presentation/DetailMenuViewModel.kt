package com.foodapps.presentation

import android.os.Bundle
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.liveData
import com.foodapps.data.model.Menu
import com.foodapps.data.repository.CartRepository
import com.foodapps.utils.ResultWrapper
import kotlinx.coroutines.Dispatchers

class DetailMenuViewModel(
    private val extras: Bundle?,
    private val cartRepository: CartRepository
) : ViewModel() {

    val menu = extras?.getParcelable<Menu>(DetailMenuActivity.EXTRA_MENU)

    private val _menuCountLiveData = MutableLiveData<Int>()
    val menuCountLiveData: LiveData<Int>
        get() = _menuCountLiveData

    val priceLiveData = MutableLiveData<Double>().apply {
        postValue(0.0)
    }

    fun add() {
        val count = (menuCountLiveData.value ?: 0) + 1
        _menuCountLiveData.postValue(count)
        priceLiveData.postValue(menu?.price?.times(count) ?: 0.0)
    }

    fun minus() {
        if ((menuCountLiveData.value ?: 0) > 0) {
            val count = (menuCountLiveData.value ?: 0) - 1
            _menuCountLiveData.postValue(count)
            priceLiveData.postValue(menu?.price?.times(count) ?: 0.0)
        }
    }

    fun addToCart(): LiveData<ResultWrapper<Boolean>> {
        return menu?.let {
            val quantity = menuCountLiveData.value ?: 0
            cartRepository.createCart(it, quantity).asLiveData(Dispatchers.IO)
        } ?: liveData {
            emit(ResultWrapper.Error(IllegalAccessException("Menu not found")))
        }
    }
}