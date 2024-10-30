package com.example.wfrleytask.repository

import android.util.Log
import com.example.wfrleytask.data.remote.ApiClient
import com.example.wfrleytask.model.Item
import com.example.wfrleytask.model.OrderDetailsResponse
import com.example.wfrleytask.model.PagingRequest
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class OrdersRepository @Inject
constructor(private val apiClient: ApiClient) {


    fun getAllOrders(request: PagingRequest): Flow<List<Item>> = flow {

        val response = apiClient.getAllOrders(request)
        if (response.isSuccessful) {
            emit(response.body()!!.items)
        }
    }

    fun getOrderDetails(orderId: Int, customerId: String): Flow<OrderDetailsResponse> = flow {
        val response = apiClient.getOrderDetails(orderId, customerId)
        emit(response)
    }.catch { e ->
        Log.e("OrderRepository", "Error fetching order details", e)
    }
}
