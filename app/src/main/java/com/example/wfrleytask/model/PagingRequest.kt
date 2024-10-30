package com.example.wfrleytask.model

data class PagingRequest(
    val merchantId: String,
    val pageNo: Int,
    val pageSize: Int,
    val status: Int,
    val storeId: Int
)