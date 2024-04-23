package com.foodapps.data.mapper

import com.foodapps.data.model.Category
import com.foodapps.data.source.network.model.category.CategoryItemResponse


fun CategoryItemResponse?.toCategory() =
    Category(
        id = this?.id.orEmpty(),
        name = this?.name.orEmpty(),
        imgUrl = this?.imgUrl.orEmpty(),
        slug = this?.slug.orEmpty()
    )

fun Collection<CategoryItemResponse>?.toCategories() =
    this?.map { it.toCategory() } ?: listOf()