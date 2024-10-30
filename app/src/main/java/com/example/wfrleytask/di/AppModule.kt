package com.example.wfrleytask.di

import com.example.wfrleytask.data.remote.ApiClient
import com.example.wfrleytask.repository.OrdersRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun provideOrderRepository(apiService: ApiClient): OrdersRepository {
        return OrdersRepository(apiService)
    }


}