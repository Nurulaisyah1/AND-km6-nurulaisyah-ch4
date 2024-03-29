package com.foodapps.presentation.home

import androidx.lifecycle.ViewModel
import com.foodapps.data.repository.CategoryRepository
import com.foodapps.data.repository.MenuRepository


class HomeViewModel(
    private val categoryRepository: CategoryRepository,
    private val menuRepository: MenuRepository
) : ViewModel() {

    fun getMenus() = menuRepository.getMenus()
    fun getCategories() = categoryRepository.getCategories()

}