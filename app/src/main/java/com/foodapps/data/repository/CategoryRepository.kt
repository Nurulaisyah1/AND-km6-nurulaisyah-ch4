package com.foodapps.data.repository

import com.foodapps.data.datasource.category.CategoryDataSource
import com.foodapps.data.model.Category


interface CategoryRepository {
    fun getCategories(): List<Category>
}

class CategoryRepositoryImpl(private val dataSource: CategoryDataSource) : CategoryRepository {
    override fun getCategories(): List<Category> = dataSource.getCategories()
}