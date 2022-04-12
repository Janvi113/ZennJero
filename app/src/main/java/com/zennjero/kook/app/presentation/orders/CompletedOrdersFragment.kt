package com.zennjero.kook.app.presentation.orders

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.chip.Chip
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.zennjero.kook.app.R
import com.zennjero.kook.app.databinding.FragmentCompleteOrdersTabBinding
import com.zennjero.kook.app.domain.OrderStatus
import com.zennjero.kook.app.presentation.util.Constant
import com.zennjero.kook.app.presentation.util.Constant.ORDER_PREVIEW_DATA_KEY
import com.zennjero.kook.app.presentation.util.Utils
import com.zennjero.kook.app.presentation.util.putDataObject

class CompletedOrdersFragment : Fragment() {

    lateinit var binding: FragmentCompleteOrdersTabBinding
    lateinit var viewModel: CompletedOrdersViewModel
    val auth = Firebase.auth

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_complete_orders_tab,
            container,
            false
        )
        viewModel = ViewModelProvider(requireActivity())[CompletedOrdersViewModel::class.java]
        binding.viewmodel = viewModel


        val ordersListAdapter = OrdersListAdapter()
        binding.recyclerView.adapter = ordersListAdapter
        binding.recyclerView.layoutManager = LinearLayoutManager(activity)
        binding.recyclerView.addItemDecoration(DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL).apply {
            setDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.recycler_view_divider)!!)
        })

        ordersListAdapter.setOnItemClickListener {
            val intent = Intent(requireActivity(), OrderPreviewActivity::class.java)
            intent.putDataObject(ORDER_PREVIEW_DATA_KEY, it)
            startActivity(intent)
        }

        ordersListAdapter.setOnUpdateStatusListener { order, newStatus, onUpdateFailed ->
            viewModel.updateStatus(order.foodieId, order._id, newStatus)
                .observe(requireActivity()){
                    if(it.isSuccessful){
                        Toast.makeText(requireContext(), Constant.STATUS_UPDATED_MESSAGE, Toast.LENGTH_SHORT).show()
                    }else{
                        onUpdateFailed()
                        Toast.makeText(requireContext(), Constant.STATUS_UPDATED_MESSAGE, Toast.LENGTH_SHORT).show()
                    }
            }
        }
        val chipGroup = binding.chipGroup

        val chipDate = chipGroup.findViewById<Chip>(R.id.chipDate)
        Utils.setDate(chipDate, requireActivity(), isCompleted = true).observe(requireActivity()) {
            viewModel.date.value = it
            viewModel.getOrders()
        }

        viewModel.orders.observe(requireActivity()) {
            if (it != null) {
                ordersListAdapter.data = it
            } else {
                ordersListAdapter.data = listOf()
            }
        }

        val chipCompleted = chipGroup.findViewById<Chip>(R.id.chipCompleted)
        val chipCancelled = chipGroup.findViewById<Chip>(R.id.chipCancelled)

        chipCompleted.setOnClickListener {
            val filters = viewModel.filters.value
            if (chipCompleted.isChecked) {
                viewModel.filters.value = (if (filters != null) filters.apply { add(OrderStatus.DELIVERED) } else mutableListOf(
                    OrderStatus.DELIVERED
                ))
                // display completed orders only
            } else {
                viewModel.filters.value = (if (filters != null) filters.apply { remove(OrderStatus.DELIVERED) } else mutableListOf())
                // display all other orders
            }
            viewModel.getOrders()
        }

        chipCancelled.setOnClickListener {
            val filters = viewModel.filters.value
            if (chipCancelled.isChecked) {
                // display cancelled orders only
                viewModel.filters.value = (if (filters != null) filters.apply { add(OrderStatus.CANCELLED) } else mutableListOf(
                    OrderStatus.CANCELLED
                ))

            } else {
                // display all other orders
                viewModel.filters.value = (if (filters != null) filters.apply { remove(OrderStatus.CANCELLED) } else mutableListOf())
            }
            viewModel.getOrders()
        }

        return binding.root
    }

}