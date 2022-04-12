package com.zennjero.kook.app.presentation.orders

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.zennjero.kook.app.R
import com.zennjero.kook.app.databinding.ActivityOrderPreviewBinding
import com.zennjero.kook.app.domain.Order
import com.zennjero.kook.app.domain.OrderItem
import com.zennjero.kook.app.domain.OrderStatus
import com.zennjero.kook.app.presentation.util.Constant.ORDER_PREVIEW_DATA_KEY
import com.zennjero.kook.app.presentation.util.getDataObjectExtra

class OrderPreviewActivity : AppCompatActivity() {

    lateinit var binding: ActivityOrderPreviewBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_order_preview)
        binding.backButton.setOnClickListener {
            onBackPressed()
        }
        setUpData()
    }

    private fun setUpData() {
        val intent = intent
        val order = intent.getDataObjectExtra(ORDER_PREVIEW_DATA_KEY,Order::class.java)
        setupRecyclerview(order.dishes.toList())
        var nameformat =" : "
        nameformat+=order.foodieName
        binding.apply {
            orderNo.text = order.orderNo.toString()
            totalPrice.text = order.billAmount.toString()
            customerName.text = nameformat
        }
        when(order.status)
        {
            OrderStatus.DELIVERED -> binding.statusImage.setImageResource(R.drawable.ic_completed)
            OrderStatus.CANCELLED -> binding.statusImage.setImageResource(R.drawable.ic_cancelled)
            OrderStatus.ON_THE_WAY -> binding.statusImage.setImageResource(R.drawable.ic_out_for_delivery)
            OrderStatus.PREPARING -> binding.statusImage.setImageResource(R.drawable.ic_preparing)
            else -> binding.statusImage.setImageResource(R.drawable.ic_cancelled)
        }
    }

    private fun setupRecyclerview(data:List<OrderItem>) {
       binding.recyclerview.apply {
            adapter = OrderPreviewRecyclerviewAdapter(data)
            layoutManager = LinearLayoutManager(this@OrderPreviewActivity)
        }
    }
}