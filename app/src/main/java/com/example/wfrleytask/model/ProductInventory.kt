package com.example.wfrleytask.model

data class ProductInventory(
    val id: Int,
    val inventoryId: Int,
    val price: Double,
    val priceAfterDiscount: Double,
    val productId: Int,
    val quantity: Double,
    val salableQuantity: Double,
    val status: Boolean
)