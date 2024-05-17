package com.foodapps.data.network.model.checkout

import com.google.gson.annotations.SerializedName

data class CheckoutItemPayload(
    @SerializedName("notes")
    val notes: String?,
    @SerializedName("menu_id")
    val productId: String,
    @SerializedName("qty")
    val quantity: Int,
)
