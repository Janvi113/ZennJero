package com.zennjero.kook.presentation.menu

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.zennjero.kook.app.R
import com.zennjero.kook.app.databinding.FragmentMasterMenuBinding
import com.zennjero.kook.app.presentation.menu.MasterMenuListAdapter
import com.zennjero.kook.app.presentation.menu.MasterMenuViewModel
import com.zennjero.kook.app.presentation.util.hide
import com.zennjero.kook.app.presentation.util.show

class MasterMenuFragment : Fragment() {

    //Data Binding
    private lateinit var binding: FragmentMasterMenuBinding

    private lateinit var viewModel: MasterMenuViewModel

    private lateinit var madapter: MasterMenuListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_master_menu, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(requireActivity())[MasterMenuViewModel::class.java]
        binding.viewModel =viewModel
        viewModel.count.value = 0
        madapter = MasterMenuListAdapter()
        binding.progressLayout.show()
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = madapter
        }
        Observers()
        viewModel.getMenuItemList()
        madapter.setOnItemClickListener {
            //println(it)
            val check = viewModel.selected_item_check[it]
            if(check == false)
            {
                viewModel.count.value = viewModel.count.value!! + 1
                viewModel.selected_item_check[it] = true
                viewModel.selected_items.add(it)
            }
            else if(check == true)
            {
                viewModel.count.value = viewModel.count.value!! - 1
                viewModel.selected_item_check[it] = false
                viewModel.selected_items.remove(it)
            }
        }
    }



    private fun Observers()
    {
        viewModel.data.observe(requireActivity()){
            madapter.differ.submitList(it)
            binding.progressLayout.hide()
            viewModel.selected_item_check.clear()
            viewModel.selected_items.clear()
            for(item in it)
            {
                viewModel.selected_item_check.put(item,false)
            }
        }
        viewModel.count.observe(requireActivity()){
            val st = "(Total $it items selected)"
            binding.selectedItemCount.text = st
        }
    }
}