package com.foodapps.data.network.model.checkout

import androidx.annotation.Keep
import com.foodapps.data.network.model.CheckoutItemPayload
import com.google.gson.annotations.SerializedName

@Keep
data class CheckoutRequestPayload(
    @SerializedName("username")
    val username: String? ,
    @SerializedName("orders")
    val orders: List<CheckoutItemPayload>? ,
    @SerializedName("total")
    val total: Int?
)
