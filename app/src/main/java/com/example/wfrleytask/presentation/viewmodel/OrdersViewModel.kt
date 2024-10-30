package com.example.wfrleytask.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wfrleytask.model.Item
import com.example.wfrleytask.model.OrderDetailsResponse
import com.example.wfrleytask.model.PagingRequest
import com.example.wfrleytask.repository.OrdersRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class OrdersViewModel @Inject
constructor(private val repository: OrdersRepository) : ViewModel() {

    private val _orderDetail = MutableStateFlow<OrderDetailsResponse?>(null)
    val orderDetail: StateFlow<OrderDetailsResponse?> = _orderDetail.asStateFlow()

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


}