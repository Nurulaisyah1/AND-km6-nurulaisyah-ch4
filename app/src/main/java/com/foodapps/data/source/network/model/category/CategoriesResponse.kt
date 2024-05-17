package com.foodapps.data.network.model.category

import androidx.annotation.Keep
import com.foodapps.data.source.network.model.category.CategoryItemResponse
import com.google.gson.annotations.SerializedName

@Keep
data class CategoriesResponse(
    @SerializedName("code")
    val code: Int?,
    @SerializedName("data")
    val data: List<CategoryItemResponse>?,
    @SerializedName("message")
    val message: String?,
    @SerializedName("status")
    val status: Boolean?,
)
