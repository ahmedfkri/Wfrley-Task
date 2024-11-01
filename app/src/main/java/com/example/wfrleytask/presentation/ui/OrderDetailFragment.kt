package com.example.wfrleytask.presentation.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.wfrleytask.databinding.FragmentOrderDetailBinding
import com.example.wfrleytask.presentation.adapter.OrderDetailsAdapter
import com.example.wfrleytask.presentation.viewmodel.OrdersViewModel
import com.example.wfrleytask.util.Constants.MERCHANT_ID
import com.example.wfrleytask.util.DateTimeUtil


class OrderDetailFragment : Fragment() {


    lateinit var binding: FragmentOrderDetailBinding
    lateinit var ordersViewModel: OrdersViewModel
    lateinit var orderDetailsAdapter: OrderDetailsAdapter
    private var itemId: Int? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentOrderDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        ordersViewModel = (activity as MainActivity).ordersViewModel
        itemId = arguments?.getInt("item_id")
        Log.d("TAG", "onViewCreated: $itemId")

        getOrderDetails(itemId)
        setupRecyclerView()
        onBack()


    }

    private fun onBack() {
        binding.btnBack.setOnClickListener {
            requireActivity().onBackPressed()
        }
        binding.btnHome.setOnClickListener {
            requireActivity().onBackPressed()
        }
    }


    private fun setupRecyclerView() {
        orderDetailsAdapter = OrderDetailsAdapter()
        binding.rvOrderElemnts.adapter = orderDetailsAdapter
        binding.rvOrderElemnts.layoutManager = LinearLayoutManager(requireContext())
    }

    private fun getOrderDetails(itemId: Int?) {

        ordersViewModel.getOrderDetails(itemId!!, MERCHANT_ID)
        lifecycleScope.launchWhenStarted {
            ordersViewModel.orderDetail.collect { response ->
                Log.d("TAG", "getOrderDetails: $response")
                if (response != null) {
                    binding.apply {
                        binding.scrollOrderDetail.visibility = View.VISIBLE
                        txtBillNumber.text = buildString {
                            append(response.id)
                            append("#")
                        }
                        txtClientName.text = response.customerUser?.displayName ?: "عميل"
                        txtClientPhone.text = response.shippingAddresses[0].telephone
                        txtClientAdress.text = response.shippingAddresses[0].cityName
                        txtPaymentMethod.text =
                            if (response.paymentMethod == 1) "نقدي" else "فيزا"
                        txtTotalDiscount.text = response.totalRefunded.toString()
                        txtTotalPrice.text = response.grandTotal.toString()
                        txtTodayDate.text =
                            DateTimeUtil.formatDateToArabic(response.createdDate.toString())
                    }
                    orderDetailsAdapter.differ.submitList(response.orderDetails)
                } else {
                    binding.scrollOrderDetail.visibility = View.GONE
                }
            }
        }


    }
}