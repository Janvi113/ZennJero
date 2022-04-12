package com.zennjero.kook.app.presentation.orders

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.zennjero.kook.app.databinding.OrderPreviewListItemviewBinding
import com.zennjero.kook.app.domain.OrderItem

class OrderPreviewRecyclerviewAdapter (private val data : List<OrderItem>) : RecyclerView.Adapter<OrderPreviewRecyclerviewAdapter.MyViewHolder>() {
    inner class MyViewHolder(val binding:OrderPreviewListItemviewBinding):RecyclerView.ViewHolder(binding.root){
        fun bind(item:OrderItem)
        {
            binding.listitem = item
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
          val inflater =  LayoutInflater.from(parent.context)
          val listItemviewBinding = OrderPreviewListItemviewBinding.inflate(inflater,parent,false)
          return MyViewHolder(listItemviewBinding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount(): Int {
        return data.size
    }
}