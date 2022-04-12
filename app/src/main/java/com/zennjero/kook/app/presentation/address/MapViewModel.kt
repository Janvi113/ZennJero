package com.zennjero.kook.app.presentation.address


import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.zennjero.kook.app.domain.Location

class MapViewModel : ViewModel(){

    var addressLine:String? = null
    var location: Location? = null

    val resultAvailable = MutableLiveData<Boolean>()
}