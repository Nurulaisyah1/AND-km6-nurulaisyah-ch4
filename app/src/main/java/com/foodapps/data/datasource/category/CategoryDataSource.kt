package com.foodapps.data.datasource.category

import com.foodapps.data.network.model.category.CategoriesResponse

interface CategoryDataSource {
    suspend fun getCategories(): CategoriesResponse
}