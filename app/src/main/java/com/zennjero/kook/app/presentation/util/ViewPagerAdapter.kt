package com.zennjero.kook.app.presentation.util

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.zennjero.kook.app.presentation.util.Constant.NUM_TABS
import com.zennjero.kook.app.presentation.orders.CompletedOrdersFragment
import com.zennjero.kook.app.presentation.orders.UpcomingOrdersFragment

class ViewPagerAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) : FragmentStateAdapter(fragmentManager, lifecycle) {
    override fun getItemCount(): Int {
        return NUM_TABS
    }

    override fun createFragment(position: Int): Fragment {
        if (position == 0){
            return CompletedOrdersFragment()
        }
        return UpcomingOrdersFragment()
    }

}