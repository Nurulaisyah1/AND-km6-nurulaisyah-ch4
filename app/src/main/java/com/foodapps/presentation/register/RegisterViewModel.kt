package com.foodapps.presentation.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.foodapps.data.repository.UserRepository
import com.foodapps.utils.ResultWrapper
import kotlinx.coroutines.Dispatchers

class RegisterViewModel(private val repository: UserRepository) : ViewModel() {
    fun doRegister(
        name: String,
        email: String,
        password: String,
    ): LiveData<ResultWrapper<Boolean>> {
        return repository.doRegister(name, email, password).asLiveData(Dispatchers.IO)
    }
}