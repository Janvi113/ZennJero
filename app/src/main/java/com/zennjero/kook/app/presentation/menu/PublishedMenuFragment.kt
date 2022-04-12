package com.zennjero.kook.presentation.menu

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.zennjero.kook.app.R
import com.zennjero.kook.app.databinding.FragmentPublishedMenuBinding
import com.zennjero.kook.app.presentation.menu.PublishedMenuListAdapter

class PublishedMenuFragment: Fragment() {

    lateinit var binding: FragmentPublishedMenuBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_published_menu,
            container,
            false
        )

        val menuListAdapter = PublishedMenuListAdapter()
//        val dataList = ArrayList<PublishedMenu>()
//        dataList.add(PublishedMenu(PublishedMenuListAdapter.VIEW_TYPE_DATE, "26 April"))
//        dataList.add(PublishedMenu(PublishedMenuListAdapter.VIEW_TYPE_MENU_ITEM, "8:30 to 10:30"))
//        dataList.add(PublishedMenu(PublishedMenuListAdapter.VIEW_TYPE_DATE, "31 May"))
//        dataList.add(PublishedMenu(PublishedMenuListAdapter.VIEW_TYPE_MENU_ITEM, "5 to 10"))
//        dataList.add(PublishedMenu(PublishedMenuListAdapter.VIEW_TYPE_MENU_ITEM, "4 to 5"))
//
//        menuListAdapter.data = dataList
        binding.recyclerView.adapter = menuListAdapter
        binding.recyclerView.layoutManager = LinearLayoutManager(activity)

        return binding.root
    }

}