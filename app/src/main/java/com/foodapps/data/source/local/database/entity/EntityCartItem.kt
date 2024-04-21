package com.foodapps.data.source.local.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cart_items")
data class EntityCartItem(
    @PrimaryKey val id: String,
    val name: String,
    val quantity: Int,
    val price: Double
)
