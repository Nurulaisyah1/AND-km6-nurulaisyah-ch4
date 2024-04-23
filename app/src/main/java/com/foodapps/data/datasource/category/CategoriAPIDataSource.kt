package com.foodapps.data.datasource.category

import com.foodapps.data.network.model.category.CategoriesResponse
import com.foodapps.data.network.services.FoodAppApiService

class CategoryApiDataSource(
    private val service: FoodAppApiService
) : CategoryDataSource {
    override suspend fun getCategories(): CategoriesResponse {
        return service.getCategories()
    }
}