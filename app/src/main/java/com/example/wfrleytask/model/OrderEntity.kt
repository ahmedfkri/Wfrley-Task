package com.example.wfrleytask.model

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "orders")
data class OrderEntity(
    @PrimaryKey val id: Int,
    val date: String,
    val price: Double,
    val clientName: String
)
