package com.foodapps.data.source.network.model.menus

import com.google.gson.annotations.SerializedName

data class MenuItemResponse(
    @SerializedName("id")
    val id: String?,
    @SerializedName("nama")
    val name: String?,
    @SerializedName("image_url")
    val imgUrl: String?,
    @SerializedName("harga")
    val price: Double?,
    @SerializedName("detail")
    val description: String?,
    @SerializedName("alamat_resto")
    val address: String?,
)
