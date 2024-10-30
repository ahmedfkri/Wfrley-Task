package com.example.wfrleytask.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.wfrleytask.databinding.OrderListItemBinding
import com.example.wfrleytask.model.Item
import com.example.wfrleytask.util.DateTimeUtil


class OrdersAdapter : ListAdapter<Item, OrdersAdapter.ItemViewHolder>(OrdersDiffUtil()) {

    var onItemClick: ((Item) -> Unit)? = null

    class OrdersDiffUtil : DiffUtil.ItemCallback<Item>() {
        override fun areItemsTheSame(oldItem: Item, newItem: Item): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Item, newItem: Item): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, OrdersDiffUtil())


    class ItemViewHolder(val binding: OrderListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ItemViewHolder {
        return ItemViewHolder(
            OrderListItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val currentItem = differ.currentList[position]
        holder.binding.apply {
            txtOrderPrice.text = currentItem.grandTotal.toString() + " ج.م "
            txtOrderDate.text = DateTimeUtil(currentItem.createdDate.toString())
            currentItem.customerUser?.displayName.let {
                txtClientName.text = it
            }
        }
        holder.itemView.setOnClickListener {
            onItemClick?.invoke(currentItem)
        }
    }

    override fun getItemCount() = differ.currentList.size
}