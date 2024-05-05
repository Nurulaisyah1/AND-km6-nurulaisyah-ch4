package com.foodapps.presentation.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.foodapps.data.model.User
import com.foodapps.data.repository.CategoryRepository
import com.foodapps.data.repository.MenuRepository
import com.foodapps.data.repository.PrefRepository
import com.foodapps.data.repository.UserRepository
import kotlinx.coroutines.Dispatchers

class HomeViewModel(
    private val categoryRepository: CategoryRepository,
    private val menuRepository: MenuRepository,
    private val prefRepository: PrefRepository,
    private val userRepository: UserRepository,
) : ViewModel() {
    private val _isUsingGridMode = MutableLiveData(false)
    val isUsingGridMode: LiveData<Boolean>
        get() = _isUsingGridMode

    init {
        _isUsingGridMode.value = isUsingGridMode()
    }

    fun getMenus(category: String? = null) =
        menuRepository.getMenus(category).asLiveData(Dispatchers.IO)

    fun getCategories() = categoryRepository.getCategories().asLiveData(Dispatchers.IO)
    fun changeListMode() {
        val currentValue = _isUsingGridMode.value ?: false
        _isUsingGridMode.postValue(!currentValue)
        setUsingGridMode(!currentValue)
    }

    private fun isUsingGridMode() = prefRepository.isUsingGridMode()

    private fun setUsingGridMode(isUsingGridMode: Boolean) = prefRepository.setUsingGridMode(isUsingGridMode)

    fun getUser(): User? {
        return userRepository.getCurrentUser()
    }

    fun isLoggedIn(): Boolean {
        return userRepository.isLoggedIn()
    }
}
