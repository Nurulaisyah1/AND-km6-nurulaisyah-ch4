package com.foodapps.data.datasource.category

import com.foodapps.data.model.Category

interface CategoryDataSource {

    fun getCategories(): List<Category>
}