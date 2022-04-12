package com.zennjero.kook.presentation.orders

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayoutMediator
import com.zennjero.kook.app.R
import com.zennjero.kook.app.databinding.FragmentOrdersTabBinding
import com.zennjero.kook.app.presentation.util.Constant.COMPLETED
import com.zennjero.kook.app.presentation.util.Constant.UPCOMING
import com.zennjero.kook.app.presentation.util.ViewPagerAdapter

val tabsArray = arrayOf(COMPLETED, UPCOMING)

class OrdersTabFragment : Fragment() {
    lateinit var binding: FragmentOrdersTabBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_orders_tab,
            container,
            false
        )

        binding.viewpager.adapter =
            activity?.let { ViewPagerAdapter(it.supportFragmentManager, lifecycle) }

        TabLayoutMediator(binding.tabLayout, binding.viewpager) { tab, position ->
            tab.text = tabsArray[position]
        }.attach()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

}