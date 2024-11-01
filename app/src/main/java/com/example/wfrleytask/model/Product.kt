package com.example.wfrleytask.model

data class Product(
    val additionalBarcode: String?,
    val alwaysAvailable: Boolean?,
    val barCode: String?,
    val childQuantity: Int?,
    val id: Int,
    val image: String?,
    val isAvailable: Boolean?,
    val merchantProducts: List<MerchantProduct>?,
    val name: String?,
    val price: Double?,
    val priceAfterDiscount: Double?,
    val productCategories: List<ProductCategory>,
    val productInventories: List<ProductInventory>,
    val productType: Int?,
    val salableQuantity: Double?,
    val sku: String?,
    val subCategoryId: Int?
)