package com.foodapps.presentation.DetailMenu

import android.os.Bundle
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.foodapps.data.model.Menu
import com.foodapps.data.repository.CartRepository
import com.foodapps.utils.ResultWrapper
import kotlinx.coroutines.launch


class DetailMenuViewModel(
    private val extras: Bundle?,
    private val cartRepository: CartRepository
) : ViewModel() {

    val menu: Menu? = extras?.getParcelable(Menu.EXTRA_MENU)

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
        val resultLiveData = MutableLiveData<ResultWrapper<Boolean>>()
        menu?.let { menu ->
            val quantity = menuCountLiveData.value ?: 0
            viewModelScope.launch {
                try {
                    val result = cartRepository.createCart(menu, quantity)
                    resultLiveData.postValue(ResultWrapper.Success(result))
                } catch (e: Exception) {
                    resultLiveData.postValue(ResultWrapper.Error(e))
                }
            }
        } ?: run {
            resultLiveData.postValue(ResultWrapper.Error(IllegalAccessException("Menu not found")))
        }
        return resultLiveData
    }
}

private fun <T> MutableLiveData<T>.postValue(success: ResultWrapper.Success<T>) {
    TODO("Not yet implemented")
}
