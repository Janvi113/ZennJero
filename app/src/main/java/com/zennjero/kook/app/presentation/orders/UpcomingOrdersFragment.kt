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
import com.zennjero.kook.app.R
import com.zennjero.kook.app.databinding.FragmentUpcomingOrdersTabBinding
import com.zennjero.kook.app.presentation.util.Constant
import com.zennjero.kook.app.presentation.util.Utils
import com.zennjero.kook.app.presentation.util.putDataObject

class UpcomingOrdersFragment : Fragment() {
    lateinit var binding: FragmentUpcomingOrdersTabBinding
    lateinit var viewModel: UpcomingOrdersViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_upcoming_orders_tab,
            container,
            false
        )
        val ordersListAdapter = OrdersListAdapter()
        binding.recyclerView.adapter = ordersListAdapter
        binding.recyclerView.layoutManager = LinearLayoutManager(activity)
        binding.recyclerView.addItemDecoration(DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL).apply {
            setDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.recycler_view_divider)!!)
        })
        ordersListAdapter.setOnItemClickListener {
            val intent = Intent(requireActivity(), OrderPreviewActivity::class.java)
            intent.putDataObject(Constant.ORDER_PREVIEW_DATA_KEY, it)
            startActivity(intent)
        }

        viewModel = ViewModelProvider(requireActivity())[UpcomingOrdersViewModel::class.java]
        binding.viewmodel = viewModel

        val chipGroup = binding.chipGroup
        val chipDate = chipGroup.findViewById<Chip>(R.id.chipDate)
        Utils.setDate(chipDate, requireActivity(), isCompleted = false).observe(requireActivity()) {
            viewModel.date.value = it
            viewModel.getOrders()
        }

        ordersListAdapter.setOnUpdateStatusListener { order, newStatus, onUpdateFailed ->
            viewModel.updateStatus(order.foodieId, order._id, newStatus)
                .observe(requireActivity()){
                    if(it.isSuccessful){
                        Toast.makeText(requireContext(), Constant.STATUS_UPDATED_MESSAGE, Toast.LENGTH_SHORT).show()
                    }else{
                        onUpdateFailed()
                        Toast.makeText(requireContext(), Constant.FAILED_STATUS_UPDATE_MESSAGE, Toast.LENGTH_SHORT).show()
                    }
                }
        }

        viewModel.orders.observe(requireActivity()) {
            if (it != null) {
                ordersListAdapter.data = it
            } else {
                ordersListAdapter.data = listOf()
            }
        }

        (chipGroup.findViewById<Chip>(R.id.chipCompleted)).visibility = View.GONE
        (chipGroup.findViewById<Chip>(R.id.chipCancelled)).visibility = View.GONE

        return binding.root
    }
}