package com.example.wfrleytask.model

data class ProductInvetory(
    val id: Int,
    val inventoryId: Int,
    val price: Int,
    val priceAfterDiscount: Double,
    val productId: Int,
    val quantity: Double,
    val salableQuantity: Double,
    val status: Boolean
)