package com.zennjero.kook.app.presentation.address

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zennjero.kook.app.data.repository.AddressRepository
import com.zennjero.kook.app.domain.Address
import com.zennjero.kook.app.domain.Location
import com.zennjero.kook.app.framework.network.Resource
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class AddressViewModel : ViewModel(){

    // For tracing the permission deny
    val deniedLocationPermission = MutableLiveData(false)
    //
    var houseNo = MutableLiveData<String>()
    var apartmentName = MutableLiveData<String>()
    var landmark = MutableLiveData<String>()
    var addressName = MutableLiveData<String>()

    var addressLine:String? = null
    var location: Location? = null

    private val repository = AddressRepository()

    fun uploadAddress(): MutableLiveData<Resource<Void?>> {
        val address = Address(
            addressLine = addressLine,
            location = location,
            houseNo = houseNo.value?.toInt()?:0,
            apartmentName = apartmentName.value,
            landmark = landmark.value,
            name = addressName.value
        )

        val addressUploadResult = MutableLiveData<Resource<Void?>>()

        viewModelScope.launch {
            repository.saveAddress(address = address).collect { resource ->
                addressUploadResult.postValue(resource)
            }
        }

        return addressUploadResult
    }
}