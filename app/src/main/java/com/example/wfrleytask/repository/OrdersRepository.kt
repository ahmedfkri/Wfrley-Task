package com.example.wfrleytask.repository

import android.util.Log
import com.example.wfrleytask.data.remote.ApiClient
import com.example.wfrleytask.model.CreateOrderRequest
import com.example.wfrleytask.model.CreateOrderResponse
import com.example.wfrleytask.model.Item
import com.example.wfrleytask.model.OrderDetailsResponse
import com.example.wfrleytask.model.PagingRequest
import com.example.wfrleytask.model.SearchProductRequest
import com.example.wfrleytask.model.SearchResponse
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.HttpException
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

    fun searchProducts(request: SearchProductRequest): Flow<SearchResponse> = flow {
        val response = apiClient.searchProducts(request)
        emit(response)
    }.catch { e ->
        Log.e("OrderRepository", "Error searching products", e)
    }


    fun createOrder(request: CreateOrderRequest): Flow<CreateOrderResponse> = flow {
        val response = apiClient.createOrder(request)
        emit(response)
    }.catch { throwable ->
        if (throwable is HttpException) {
            val errorBody = throwable.response()?.errorBody()?.string()
            Log.e("OrderRepository", "Error creating order: ${throwable.code()}, Body: $errorBody")
        } else {
            Log.e("OrderRepository", "Error creating order", throwable)
        }
    }

}
