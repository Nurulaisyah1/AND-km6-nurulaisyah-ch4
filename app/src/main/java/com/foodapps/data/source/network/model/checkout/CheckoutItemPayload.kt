package com.foodapps.data.network.model

import com.google.gson.annotations.SerializedName

data class CheckoutItemPayload(
    @SerializedName("notes")
    val notes: String?,
    @SerializedName("menu_id")
    val menuId: String,
    @SerializedName("qty")
    val quantity: Int,
)
