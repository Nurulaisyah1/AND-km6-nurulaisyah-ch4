package com.foodapps.data.repository

import com.foodapps.data.datasource.category.CategoryDataSource
import com.foodapps.data.mapper.toCategories
import com.foodapps.data.model.Category
import com.foodapps.utils.ResultWrapper
import com.foodapps.utils.proceedFlow
import kotlinx.coroutines.flow.Flow




interface CategoryRepository {
    fun getCategories(): Flow<ResultWrapper<List<Category>>>
}

class CategoryRepositoryImpl(
    private val dataSource: CategoryDataSource
) : CategoryRepository {
    override fun getCategories(): Flow<ResultWrapper<List<Category>>> {
        return proceedFlow { dataSource.getCategories().data.toCategories() }
    }
}