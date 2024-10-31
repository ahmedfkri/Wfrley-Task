package com.example.wfrleytask.presentation.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.wfrleytask.R
import com.example.wfrleytask.databinding.FragmentHomeBinding
import com.example.wfrleytask.model.PagingRequest
import com.example.wfrleytask.presentation.adapter.OrdersAdapter
import com.example.wfrleytask.presentation.viewmodel.OrdersViewModel
import com.example.wfrleytask.util.Constants.MERCHANT_ID
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    lateinit var ordersViewModel: OrdersViewModel
    lateinit var ordersAdapter: OrdersAdapter

    private var currentPage = 1
    private var isLoading = false
    private var isLastPage = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ordersViewModel = (activity as MainActivity).ordersViewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        setupRecyclerView()
        setupItemClickListener()
        onCreateOrderClick()
        loadOrders()

    }

    private fun loadOrders() {
        if (isLoading || isLastPage) return
        isLoading = true

        val request = PagingRequest(MERCHANT_ID, currentPage, 10, 0, 5)
        viewLifecycleOwner.lifecycleScope.launch {
            ordersViewModel.getAllOrders(request).collect { orders ->
                if (orders.isNotEmpty()) {
                    val currentList = ordersAdapter.differ.currentList
                    val filteredOrders = orders.filter { newOrder ->
                        currentList.none { it.id == newOrder.id }
                    }
                        ordersAdapter.differ.submitList(currentList + filteredOrders)
                        currentPage++
                    } else {
                        isLastPage = true
                    }
                    isLoading = false
                    Log.d("tagtagtag", "onViewCreated: $orders")
                }
            }
        }

        private fun onCreateOrderClick() {
            binding.cvAddOrder.setOnClickListener {
                findNavController().navigate(R.id.action_homeFragment_to_createOrderFragment)
            }
        }

        private fun setupItemClickListener() {
            ordersAdapter.onItemClick = {
                val bundle = Bundle().apply {
                    putInt("item_id", it.id)
                }
                findNavController().navigate(
                    R.id.action_homeFragment_to_orderDetailFragment,
                    bundle
                )

            }
        }


        private fun setupRecyclerView() {
            ordersAdapter = OrdersAdapter()
            binding.rvOrders.adapter = ordersAdapter
            binding.rvOrders.layoutManager = LinearLayoutManager(requireContext())

            binding.rvOrders.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                    if (layoutManager.findLastCompletelyVisibleItemPosition() == ordersAdapter.itemCount - 1) {
                        loadOrders()
                    }
                }
            })
        }

    }