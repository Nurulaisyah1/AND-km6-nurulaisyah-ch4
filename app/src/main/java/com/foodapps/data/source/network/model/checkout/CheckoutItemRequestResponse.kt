package com.foodapps.data.source.network.model.checkout

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class CheckoutItemRequestResponse(
    @field:SerializedName("name")
    val name: String? = null,
    @field:SerializedName("price")
    val priceItem: Int? = null,
    @field:SerializedName("qty")
    val qty: Int? = null,
    @field:SerializedName("notes")
    val notes: String? = null,
)
