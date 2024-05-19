package com.foodapps.data.source.network.model.menus

import com.google.gson.annotations.SerializedName

data class MenuItemResponse(
    @SerializedName("id")
    val id: String?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("image_url")
    val imgUrl: String?,
    @SerializedName("price")
    val price: Double?,
    @SerializedName("detail")
    val description: String?,
    @SerializedName("address")
    val address: String?,
)
