package com.foodapps.presentation.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.foodapps.data.repository.UserRepository
import com.foodapps.utils.ResultWrapper

class LoginViewModel(private val repository: UserRepository) : ViewModel() {

    fun doLogin(email: String, password: String): LiveData<ResultWrapper<Boolean>> {
        return repository.doLogin(email, password).asLiveData()
    }
}