package com.foodapps.presentation.detailmenu

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
    private val intent: Bundle?,
    private val cartRepository: CartRepository
) : ViewModel() {
    val menu = intent?.getParcelable<Menu>(DetailMenuActivity.EXTRA_ITEM)

    val menuCountLiveData = MutableLiveData(0).apply {
        postValue(0)
    }

    val priceLiveData = MutableLiveData<Double>().apply {
        postValue(0.0)
    }

    init {
        bindMenu(menu)
    }

    private fun bindMenu(menu: Menu?) {
        menu?.let {
            priceLiveData.postValue(0.0)
        }
    }

    fun add() {
        val count = (menuCountLiveData.value ?: 0) + 1
        menuCountLiveData.postValue(count)
        priceLiveData.postValue((menu?.price ?: 0.0) * count)
    }

    fun minus() {
        if ((menuCountLiveData.value ?: 0) > 0) {
            val count = (menuCountLiveData.value ?: 0) - 1
            menuCountLiveData.postValue(count)
            priceLiveData.postValue((menu?.price ?: 0.0) * count)
        }
    }

    fun addToCart(): LiveData<ResultWrapper<Boolean>> {
        return menu?.let {
            val quantity = menuCountLiveData.value ?: 0
            cartRepository.createCart(it, quantity).asLiveData(Dispatchers.IO)
        } ?: liveData { emit(ResultWrapper.Error(IllegalStateException("Menu not found"))) }
    }
}
