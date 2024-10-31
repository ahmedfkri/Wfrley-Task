package com.example.wfrleytask.di

import android.content.Context
import androidx.room.Room
import com.example.wfrleytask.data.local.OrderDatabase
import com.example.wfrleytask.data.local.OrdersDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): OrderDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            OrderDatabase::class.java,
            "order_database"
        ).fallbackToDestructiveMigration().build()
    }

    @Provides
    fun provideOrderDao(database: OrderDatabase): OrdersDao {
        return database.orderDao()
    }
}