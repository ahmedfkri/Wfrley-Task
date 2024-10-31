package com.example.wfrleytask.presentation.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.wfrleytask.R
import com.example.wfrleytask.databinding.FragmentCreateOrderBinding
import com.example.wfrleytask.model.SearchProductRequest
import com.example.wfrleytask.presentation.adapter.ProductsAdapter
import com.example.wfrleytask.presentation.viewmodel.OrdersViewModel
import com.example.wfrleytask.util.Constants
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class CreateOrderFragment : Fragment() {

    lateinit var binding: FragmentCreateOrderBinding
    lateinit var viewModel: OrdersViewModel
    lateinit var productsAdapter: ProductsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCreateOrderBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = (activity as MainActivity).ordersViewModel

        setupRecyclerView()
        onSearch()
        fetchSelectedProducts()
        onFinishOrderClick()

    }

    private fun onFinishOrderClick() {
        binding.cvFinishOrder.setOnClickListener {
            findNavController().navigate(R.id.action_createOrderFragment_to_finishOrderFragment)

        }
    }

    private fun fetchSelectedProducts() {
        lifecycleScope.launchWhenStarted {
            viewModel.selectedProducts.collect { selectedProducts ->
                when (selectedProducts.size) {
                    0 -> {
                        binding.cvFinishOrder.visibility = View.GONE
                    }

                    else -> {
                        binding.cvFinishOrder.visibility = View.VISIBLE
                        val totalPrice: Double = selectedProducts.sumOf { it.rowTotal }
                        val count = selectedProducts.sumOf { it.quantity }
                        binding.txtTotalOrderItemsCount.text = count.toInt().toString()
                        binding.txtTotalOrderPrice.text = "$totalPrice ج.م "
                    }

                }
            }
        }
    }

    private fun onSearch() {
        var searchJob: Job? = null
        binding.edtSearch.addTextChangedListener {
            searchJob?.cancel()
            searchJob = lifecycleScope.launch {
                delay(500L)
                searchProduct(it.toString())
            }
        }

    }

    private fun searchProduct(productName: String) {
        val request = SearchProductRequest(
            merchantId = Constants.MERCHANT_ID,
            name = productName,
            storeId = 5
        )
        lifecycleScope.launch {
            viewModel.searchProducts(request).collect { response ->
                productsAdapter.differ.submitList(response)
                Log.d("SearchProduct", response.toString())
            }
        }

    }

    private fun setupRecyclerView() {
        productsAdapter = ProductsAdapter(viewModel, viewLifecycleOwner)
        binding.rvSearchedProducts.apply {
            adapter = productsAdapter
        }

    }

}