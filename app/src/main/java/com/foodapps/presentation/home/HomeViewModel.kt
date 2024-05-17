package com.foodapps.presentation.home

import androidx.lifecycle.*
import com.foodapps.data.repository.CategoryRepository
import com.foodapps.data.repository.MenuRepository
import kotlinx.coroutines.Dispatchers

class HomeViewModel(
    private val categoryRepository: CategoryRepository,
    private val menuRepository: MenuRepository,
) : ViewModel() {
    fun getMenus(categorySlug: String? = null) = menuRepository.getMenus(categorySlug).asLiveData(Dispatchers.IO)

    fun getCategories() = categoryRepository.getCategories().asLiveData(Dispatchers.IO)
}
