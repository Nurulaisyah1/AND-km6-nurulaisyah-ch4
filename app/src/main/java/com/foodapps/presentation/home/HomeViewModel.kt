package com.foodapps.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.foodapps.data.model.Menu
import com.foodapps.data.repository.CategoryRepository
import com.foodapps.data.repository.MenuRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class HomeViewModel(
    private val categoryRepository: CategoryRepository,
    private val menuRepository: MenuRepository
) : ViewModel() {

    // Menampilkan semua menu atau menu berdasarkan kategori tertentu
    suspend fun getMenus(categorySlug: String? = null): Flow<List<Menu>>? {
        return categorySlug?.let { menuRepository.getMenusByCategory(it) }
    }

    // Menampilkan semua kategori
    fun getCategories() = categoryRepository.getCategories()

    // Menambahkan menu baru
    fun addMenu(menu: Menu) {
        viewModelScope.launch {
            menuRepository.addMenu(menu)
        }
    }

    // Memperbarui menu yang ada
    fun updateMenu(menu: Menu) {
        viewModelScope.launch {
            menuRepository.updateMenu(menu)
        }
    }

    // Menghapus menu
    fun deleteMenu(menu: Menu) {
        viewModelScope.launch {
            menuRepository.deleteMenu(menu)
        }
    }
}
