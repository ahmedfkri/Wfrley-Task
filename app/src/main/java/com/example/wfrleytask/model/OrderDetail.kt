package com.example.wfrleytask.model

data class OrderDetail(
    val id: Int,
    val product: Product,
    val productCustomizationId: Any,
    val productCustomizations: Any,
    val quantity: Double,
    val rowPriceAfterDiscount: Double,
    val rowTotal: Double,
    val syncSucceed: Boolean
)