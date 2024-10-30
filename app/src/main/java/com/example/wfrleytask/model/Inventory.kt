package com.example.wfrleytask.model

data class Inventory(
    val id: Int,
    val name: String,
    val store: Store,
    val storeId: Int
)