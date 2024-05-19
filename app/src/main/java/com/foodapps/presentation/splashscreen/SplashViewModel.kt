package com.foodapps.presentation.splashscreen
import androidx.lifecycle.ViewModel
import com.foodapps.data.repository.UserRepository

class SplashViewModel(private val repository: UserRepository) : ViewModel() {
    fun isLoggedIn(): Boolean {
        return repository.isLoggedIn()
    }
}