package com.example.wfrleytask.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wfrleytask.model.CreateOrderRequest
import com.example.wfrleytask.model.CreateOrderResponse
import com.example.wfrleytask.model.Item
import com.example.wfrleytask.model.OrderDetail
import com.example.wfrleytask.model.OrderDetailsResponse
import com.example.wfrleytask.model.PagingRequest
import com.example.wfrleytask.model.Product
import com.example.wfrleytask.model.SearchProductRequest
import com.example.wfrleytask.model.SearchResponse
import com.example.wfrleytask.repository.OrdersRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class OrdersViewModel @Inject
constructor(private val repository: OrdersRepository) : ViewModel() {

    private val _orderDetail = MutableStateFlow<OrderDetailsResponse?>(null)
    val orderDetail: StateFlow<OrderDetailsResponse?> = _orderDetail.asStateFlow()

    private val _selectedProducts = MutableStateFlow<MutableList<OrderDetail>>(mutableListOf())
    val selectedProducts: StateFlow<List<OrderDetail>> = _selectedProducts


    fun getAllOrders(request: PagingRequest): Flow<List<Item>> {
        return repository.getAllOrders(request)
    }

    fun getOrderDetails(orderId: Int, customerId: String) {
        viewModelScope.launch {
            repository.getOrderDetails(orderId, customerId).collect { response ->
                _orderDetail.value = response
            }
        }
    }

    fun searchProducts(request: SearchProductRequest): Flow<SearchResponse> {
        return repository.searchProducts(request)
    }


    fun selectProduct(product: Product, quantity: Double) {
        val orderDetail = OrderDetail(
            id = product.id,
            product = product,
            quantity = quantity,
            productCustomizationId = 0,
            productCustomizations = "",
            rowPriceAfterDiscount = product.priceAfterDiscount ?: 0.0,
            rowTotal = (product.price ?: 0.0) * quantity,
            syncSucceed = true
        )

        _selectedProducts.update { currentList ->
            val updatedList = currentList.toMutableList()
            val existingIndex = updatedList.indexOfFirst { it.id == product.id }
            if (existingIndex >= 0) {
                updatedList[existingIndex] = orderDetail
            } else {
                updatedList.add(orderDetail)
            }
            updatedList
        }
    }

    fun removeProduct(product: Product) {
        _selectedProducts.update { currentList ->
            currentList.filter { it.id != product.id }.toMutableList()
        }
    }

    fun createOrder(createOrderRequest: CreateOrderRequest): Flow<CreateOrderResponse> {
        return repository.createOrder(createOrderRequest)
    }


}