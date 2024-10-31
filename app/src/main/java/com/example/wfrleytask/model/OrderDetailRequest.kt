package com.example.wfrleytask.model

data class OrderDetailRequest(
    val productId: Int,
    val quantity: Double,
    val rowPriceAfterDiscount: Double,
    val rowTotal: Double
)