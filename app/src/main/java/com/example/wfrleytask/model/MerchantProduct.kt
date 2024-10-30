package com.example.wfrleytask.model

data class MerchantProduct(
    val additionalBarcode: Any,
    val alwaysAvailable: Boolean,
    val childQuantity: Any,
    val dateFrom: String,
    val dateTo: String,
    val description: String,
    val initialValue: Double,
    val isMostUsed: Boolean,
    val isOffer: Boolean,
    val maxQuantity: Int,
    val price: Double,
    val priceAfterDiscount: Double,
    val productApparence: Int,
    val productApprovalStatus: Int,
    val status: Int
)