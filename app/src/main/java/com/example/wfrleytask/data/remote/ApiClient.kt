package com.example.wfrleytask.data.remote

import com.example.wfrleytask.model.Orders
import com.example.wfrleytask.model.PagingRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST



interface ApiClient {

    @POST("GetAllWithPaging")
    suspend fun getAllOrders(@Body request: PagingRequest): Response<Orders>

}