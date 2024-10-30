package com.example.wfrleytask.model

data class Store(
    val code: String,
    val id: Int,
    val inventories: List<InventoryX>,
    val isActive: Boolean,
    val name: String
)