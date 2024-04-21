package com.foodapps.data.model

data class Order(
    val orderId: String,
    val customerId: String,
    val totalAmount: Double
)