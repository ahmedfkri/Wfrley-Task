package com.example.wfrleytask.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.wfrleytask.databinding.ElementListItemBinding
import com.example.wfrleytask.model.OrderDetail

class OrderDetailsAdapter() : RecyclerView.Adapter<OrderDetailsAdapter.ViewHolder>() {



    class DetailsDiffUtil : DiffUtil.ItemCallback<OrderDetail>() {
        override fun areItemsTheSame(oldItem: OrderDetail, newItem: OrderDetail): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: OrderDetail, newItem: OrderDetail): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, DetailsDiffUtil())

    class ViewHolder(val binding: ElementListItemBinding) : RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ElementListItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount() = differ.currentList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val currentProduct = differ.currentList[position].product
        val currentElement = differ.currentList[position]
        holder.binding.apply {
            txtElemntName.text = currentProduct.name
            txtElementPrice.text = currentElement.product.price.toString()
            txtElementDetail.text = buildString {
                append(currentElement.quantity.toString())
                append(" قطع")
                append(" * ")
                append(currentProduct.price.toString())
                append(" جنيه ")

            }

        }

    }


}