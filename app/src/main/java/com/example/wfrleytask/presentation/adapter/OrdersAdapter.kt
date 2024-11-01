package com.example.wfrleytask.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.wfrleytask.databinding.OrderListItemBinding
import com.example.wfrleytask.model.OrderEntity
import com.example.wfrleytask.util.DateTimeUtil
import java.util.Locale


class OrdersAdapter : ListAdapter<OrderEntity, OrdersAdapter.ItemViewHolder>(OrdersDiffUtil()) {

    var onItemClick: ((OrderEntity) -> Unit)? = null

    class OrdersDiffUtil : DiffUtil.ItemCallback<OrderEntity>() {
        override fun areItemsTheSame(oldItem: OrderEntity, newItem: OrderEntity): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: OrderEntity, newItem: OrderEntity): Boolean {
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
            txtOrderPrice.text = buildString {
        append(currentItem.price.formatWithoutTrailingZeros())
        append(" ج.م ")
    }
            txtOrderDate.text = DateTimeUtil.formatDateToArabic(currentItem.date)
            currentItem.clientName.let {
                txtClientName.text = it
            }
        }
        holder.itemView.setOnClickListener {
            onItemClick?.invoke(currentItem)
        }
    }

    override fun getItemCount() = differ.currentList.size
}

fun Double.formatWithoutTrailingZeros(): String {
    return if (this % 1.0 == 0.0) {
        this.toInt().toString()
    } else {
        String.format(Locale.getDefault(),"%.2f", this)
    }
}