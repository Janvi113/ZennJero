package com.zennjero.kook.app.presentation.menu

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zennjero.kook.app.data.repository.MasterMenuRepository
import com.zennjero.kook.app.domain.MasterMenuItem
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class MasterMenuViewModel:ViewModel() {

    val selected_item_check = mutableMapOf<MasterMenuItem,Boolean>()
    val selected_items = mutableListOf<MasterMenuItem>()
    val count = MutableLiveData<Int>()
    val data = MutableLiveData<List<MasterMenuItem>>()
    private val repository = MasterMenuRepository()
    fun getMenuItemList(){
        viewModelScope.launch {
            repository.getMasterMenu().collect {
                data.value = it.data?.masterMenuItems
            }
        }
    }

}