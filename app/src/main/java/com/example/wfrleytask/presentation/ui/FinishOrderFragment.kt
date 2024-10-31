package com.example.wfrleytask.presentation.ui

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.wfrleytask.databinding.FragmentFinishOrderBinding
import com.example.wfrleytask.model.CreateOrderRequest
import com.example.wfrleytask.model.Product
import com.example.wfrleytask.model.toOrderDetailRequest
import com.example.wfrleytask.presentation.adapter.OrderDetailsAdapter
import com.example.wfrleytask.presentation.adapter.OrdersAdapter
import com.example.wfrleytask.presentation.viewmodel.OrdersViewModel
import com.example.wfrleytask.util.Constants.CS_USER_ID
import com.example.wfrleytask.util.Constants.MERCHANT_ID
import kotlinx.coroutines.launch


class FinishOrderFragment : Fragment() {
    private lateinit var binding: FragmentFinishOrderBinding
    private lateinit var viewModel: OrdersViewModel
    private lateinit var createdOrderRequest: CreateOrderRequest
    private lateinit var orderDetailsAdapter: OrderDetailsAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = (activity as MainActivity).ordersViewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFinishOrderBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnBack.setOnClickListener {
            activity?.onBackPressed()
        }
        setupRecyclerView()
        fetchOrderData()
        addTextWatchers()
        btnFinishOrderState()
        onFinishOrderClick()


    }

    private fun setupRecyclerView() {
        orderDetailsAdapter = OrderDetailsAdapter()
        binding.rvOrderElemnts.apply {
            adapter = orderDetailsAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

    private fun addTextWatchers() {
        val textWatcher = object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun afterTextChanged(p0: Editable?) {
                btnFinishOrderState()
            }
        }
        binding.apply {
            edtClientName.addTextChangedListener(textWatcher)
            edtPhoneNmber.addTextChangedListener(textWatcher)
            edtAmountPayed.addTextChangedListener(textWatcher)
        }
    }

    private fun btnFinishOrderState() {
        binding.btnFinishOrder.isEnabled = isDataValid()
    }

    private fun onFinishOrderClick() {
        binding.btnFinishOrder.setOnClickListener {
            createOrderRequest()
        }
    }

    private fun createOrderRequest() {
        lifecycleScope.launch {
            viewModel.selectedProducts.collect { orderDetails ->
                createdOrderRequest = CreateOrderRequest(
                    addressId = 227,
                    customerId = MERCHANT_ID,
                    customerServiceUserId = CS_USER_ID,
                    orderDeliveryMethod = 1,
                    orderDetails = orderDetails.map { it.toOrderDetailRequest() }.toList(),
                    paymentDeliveryMethod = 1,
                    postponedDate = "2024-09-23T13:13:46.063Z",
                    priceAfterDiscountTotal = orderDetails.sumOf { product -> product.rowTotal },
                    storeId = 5
                )
                viewModel.createOrder(createdOrderRequest).collect {
                    Toast.makeText(requireContext(), it.id, Toast.LENGTH_SHORT).show()
                }

            }
        }
    }

    private fun isDataValid(): Boolean {
        return binding.edtClientName.text.toString()
            .isNotEmpty() && binding.edtPhoneNmber.text.toString()
            .isNotEmpty() && binding.edtAmountPayed.text.toString().isNotEmpty()
    }

    private fun fetchOrderData() {
        lifecycleScope.launch {
            viewModel.selectedProducts.collect { orderDetails ->
                binding.edtAmountPayed.setText(orderDetails.sumOf { product -> product.rowTotal }
                    .toString())
                orderDetailsAdapter.differ.submitList(orderDetails)
            }
        }
    }

}