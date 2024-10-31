package com.example.wfrleytask.model

data class CreateOrderResponse(
    val barcode: String,
    val id: Int,
    val priceAfterDiscountTotal: Int
)