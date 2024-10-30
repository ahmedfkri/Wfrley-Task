package com.example.wfrleytask.presentation.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.wfrleytask.databinding.FragmentHomeBinding
import com.example.wfrleytask.model.PagingRequest
import com.example.wfrleytask.presentation.adapter.OrdersAdapter
import com.example.wfrleytask.presentation.viewmodel.OrdersViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private val ordersViewModel: OrdersViewModel by viewModels()
    lateinit var ordersAdapter: OrdersAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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

        val request = PagingRequest("8445bef7-af04-4707-a514-13636663fd5a", 1, 10, 0, 5)


        viewLifecycleOwner.lifecycleScope.launch {
            ordersViewModel.getAllOrders(request).collect {
                ordersAdapter.differ.submitList(it)
                Log.d("TAG", "onViewCreated: $it")
            }
        }
    }



    private fun setupRecyclerView() {
        ordersAdapter = OrdersAdapter()
        binding.rvOrders.adapter = ordersAdapter
        binding.rvOrders.layoutManager = LinearLayoutManager(requireContext())
    }

}