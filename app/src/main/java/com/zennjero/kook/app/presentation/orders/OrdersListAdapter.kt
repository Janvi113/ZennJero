package com.zennjero.kook.app.presentation.orders

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.zennjero.kook.app.R
import com.zennjero.kook.app.databinding.LayoutOrderTileBinding
import com.zennjero.kook.app.domain.Order
import com.zennjero.kook.app.domain.OrderStatus
import com.zennjero.kook.app.presentation.util.setStatusUpdateListener

class OrdersListAdapter: RecyclerView.Adapter<OrdersListAdapter.OrderViewHolder>() {

    var data: List<Order> = listOf()
        set(value) {
            notifyItemRangeRemoved(0, itemCount)
            field = value
            //wholeData = value
            notifyItemRangeInserted(0, itemCount)
        }


    //private var wholeData : List<Order> = listOf()

    private var onItemClick : ((order:Order) -> Unit)? = null
    private var onUpdateStatus: ((order:Order, newStatus:OrderStatus, onUpdateFailed:()->Unit) -> Unit)? = null

    fun setOnItemClickListener(onItemClick : ((order:Order) -> Unit)?){
        this.onItemClick = onItemClick
    }

    fun setOnUpdateStatusListener(onUpdateStatus: ((order:Order, newStatus:OrderStatus, onUpdateFailed:()->Unit) -> Unit)?){
        this.onUpdateStatus = onUpdateStatus
    }

    /**
     * Filters data based on order status and returns items only
     * where order status is in the given list
     */
//    fun filterData(filters: List<OrderStatus>){
//        notifyItemRangeRemoved(0, itemCount)
//        data = wholeData.filter {
//            filters.contains(it.status)
//        } as MutableList<Order>
//        notifyItemRangeInserted(0, itemCount)
//    }

    inner class OrderViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        val binding = DataBindingUtil.bind<LayoutOrderTileBinding>(itemView)!!

        init {
            itemView.setOnClickListener {
                onItemClick?.invoke(data[adapterPosition])
            }

            binding.statusTracker.setStatusUpdateListener {
                data[adapterPosition].status = it
                binding.statusTracker.status = it
                onUpdateStatus?.invoke(data[adapterPosition], it){
                    data[adapterPosition].status = it.prevStatus
                    binding.statusTracker.status = it.prevStatus
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.layout_order_tile, parent, false)
        return OrderViewHolder(view)
    }

    override fun onBindViewHolder(holder: OrderViewHolder, position: Int) {
        holder.binding.order = data[position]
    }

    override fun getItemCount(): Int = data.size

}