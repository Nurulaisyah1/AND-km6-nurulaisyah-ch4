package com.foodapps.data.network.model.checkout

import androidx.annotation.Keep
import com.foodapps.data.source.network.model.checkout.CheckoutItemRequestResponse
import com.google.gson.annotations.SerializedName

@Keep
data class CheckoutRequestPayload(
    @field:SerializedName("total")
    val total: Int? = null,
    @field:SerializedName("orders")
    val orders: List<CheckoutItemRequestResponse>? = null,
    @field:SerializedName("username")
    val username: String? = null,
)
