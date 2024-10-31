package com.example.wfrleytask.data.remote

import com.example.wfrleytask.model.CreateOrderRequest
import com.example.wfrleytask.model.CreateOrderResponse
import com.example.wfrleytask.model.OrderDetailsResponse
import com.example.wfrleytask.model.Orders
import com.example.wfrleytask.model.PagingRequest
import com.example.wfrleytask.model.SearchProductRequest
import com.example.wfrleytask.model.SearchResponse
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

    @POST("SearchProductsForCustomer")
    suspend fun searchProducts(
        @Body request: SearchProductRequest
    ): SearchResponse

    @POST("CreateOrder")
    suspend fun createOrder(
        @Body request: CreateOrderRequest
    ): CreateOrderResponse




}