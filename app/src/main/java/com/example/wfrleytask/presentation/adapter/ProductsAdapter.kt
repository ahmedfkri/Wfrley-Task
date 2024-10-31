package com.example.wfrleytask.presentation.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.wfrleytask.R
import com.example.wfrleytask.databinding.ProductListItemBinding
import com.example.wfrleytask.model.OrderDetail
import com.example.wfrleytask.model.Product
import com.example.wfrleytask.presentation.viewmodel.OrdersViewModel


class ProductsAdapter(
    val viewModel: OrdersViewModel,
    val lifecycleOwner: LifecycleOwner
) : RecyclerView.Adapter<ProductsAdapter.ViewHolder>() {

    var onAddToOrder: ((Product) -> Unit)? = null
    var onQuantityChanged: ((Product) -> Unit)? = null
    private val items: MutableList<OrderDetail> = mutableListOf()


    class ProductDiffUtil : DiffUtil.ItemCallback<Product>() {
        override fun areItemsTheSame(
            oldItem: Product,
            newItem: Product
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: Product,
            newItem: Product
        ): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, ProductDiffUtil())


    private val selectedProductDetails: MutableMap<Int, OrderDetail> = mutableMapOf()

    init {
        lifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.selectedProducts.collect { selectedProducts ->
                Log.d("ProductsAdapter", "Collected selectedProducts: $selectedProducts")
                selectedProductDetails.clear()
                selectedProducts.forEach { orderDetail ->
                    selectedProductDetails[orderDetail.id] = orderDetail
                }
                notifyDataSetChanged()
            }
        }
    }


    class ViewHolder(val binding: ProductListItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ProductListItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount() = differ.currentList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val currentProduct = differ.currentList[position]
        val orderDetail = selectedProductDetails[currentProduct.id]
        val isSelected = orderDetail != null
        val quantity = orderDetail?.quantity ?: 0.0

        Log.d("ProductsAdapter", "Selected Product Details: $selectedProductDetails")


        holder.binding.apply {
            txtProductName.text = currentProduct.name ?: ""
            txtPrice.text = currentProduct.price?.toString() ?: ""
            txtProductCount.text = currentProduct.salableQuantity?.toString() ?: ""
            Glide.with(holder.itemView).load(currentProduct.image).into(imgProduct)

            if (isSelected) {
                btnAddToOrder.visibility = View.GONE
                loSelected.visibility = View.VISIBLE
                root.apply {
                    background.setTint(holder.itemView.context.getColor(R.color.primaryVariant))
                    strokeColor = holder.itemView.context.getColor(R.color.primary)
                }
                txtProductCurrentCount.text = quantity.toString()
                txtPrice.text = orderDetail?.rowPriceAfterDiscount.toString()
            } else {
                btnAddToOrder.visibility = View.VISIBLE
                loSelected.visibility = View.GONE
                root.apply {
                    background.setTint(holder.itemView.context.getColor(R.color.white))
                    strokeColor = holder.itemView.context.getColor(R.color.background)
                }
            }

            btnAddToOrder.setOnClickListener {
                viewModel.selectProduct(currentProduct, 1.0)
                Log.d("Product ", currentProduct.toString() + isSelected)
            }

            btnPlus.setOnClickListener {
                if (quantity == currentProduct.salableQuantity) {
                    return@setOnClickListener
                }
                val newQuantity = quantity + 1.0
                viewModel.selectProduct(currentProduct, newQuantity)
                notifyItemChanged(position)
            }

            btnMin.setOnClickListener {
                if (quantity > 1.0) {
                    val newQuantity = quantity - 1.0
                    viewModel.selectProduct(currentProduct, newQuantity)
                } else {
                    viewModel.removeProduct(currentProduct)
                }
                notifyItemChanged(position)
            }
        }
    }
}