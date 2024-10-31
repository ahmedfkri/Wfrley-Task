package com.example.wfrleytask.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.wfrleytask.model.OrderEntity

@Database(entities = [OrderEntity::class], version = 2, exportSchema = false)
abstract class OrderDatabase : RoomDatabase() {
    abstract fun orderDao(): OrdersDao

}