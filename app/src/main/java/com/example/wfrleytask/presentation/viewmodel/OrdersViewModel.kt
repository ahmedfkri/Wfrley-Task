package com.example.wfrleytask.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.example.wfrleytask.model.Item
import com.example.wfrleytask.model.PagingRequest
import com.example.wfrleytask.repository.OrdersRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


@HiltViewModel
class OrdersViewModel @Inject
constructor(private val repository: OrdersRepository) : ViewModel() {

    fun getAllOrders(request: PagingRequest): Flow<List<Item>> {
        return repository.getAllOrders(request)
    }


}