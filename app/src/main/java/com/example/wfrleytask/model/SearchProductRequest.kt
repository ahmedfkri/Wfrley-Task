package com.example.wfrleytask.model

data class SearchProductRequest(
    val merchantId: String,
    val name: String,
    val storeId: Int
)