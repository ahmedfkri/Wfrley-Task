package com.example.wfrleytask.data.remote

import com.example.wfrleytask.model.OrderDetailsResponse
import com.example.wfrleytask.model.Orders
import com.example.wfrleytask.model.PagingRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path


interface ApiClient {

    @POST("GetAllWithPaging")
    suspend fun getAllOrders(@Body request: PagingRequest): Response<Orders>

    @GET("GetOrderDetails/{orderId}/{merchanId}")
    suspend fun getOrderDetails(
        @Path("orderId") orderId: Int,
        @Path("merchanId") customerId: String
    ): OrderDetailsResponse


}