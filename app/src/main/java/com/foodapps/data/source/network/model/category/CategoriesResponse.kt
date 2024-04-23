package com.foodapps.data.network.model.category


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep
import com.foodapps.data.source.network.model.category.CategoryItemResponse

@Keep
data class CategoriesResponse(
    @SerializedName("code")
    val code: Int?,
    @SerializedName("data")
    val data: List<CategoryItemResponse>?,
    @SerializedName("message")
    val message: String?,
    @SerializedName("status")
    val status: Boolean?
)