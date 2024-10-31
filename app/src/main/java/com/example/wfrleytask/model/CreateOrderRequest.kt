package com.example.wfrleytask.model

data class CreateOrderRequest(
    val addressId: Int,
    val customerId: String,
    val customerServiceUserId: String,
    val orderDeliveryMethod: Int,
    val orderDetails: List<OrderDetailRequest>,
    val paymentDeliveryMethod: Int,
    val postponedDate: String,
    val priceAfterDiscountTotal: Double,
    val storeId: Int
)