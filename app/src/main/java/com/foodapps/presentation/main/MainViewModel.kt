package com.foodapps.presentation.main

import androidx.lifecycle.ViewModel
import com.foodapps.data.repository.UserRepository


class MainViewModel(private val repository: UserRepository) : ViewModel() {
    fun isLoggedIn(): Boolean {
        return repository.isLoggedIn()
    }
}