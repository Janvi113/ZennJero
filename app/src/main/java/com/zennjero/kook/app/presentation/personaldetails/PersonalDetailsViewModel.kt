package com.zennjero.kook.app.presentation.personaldetails

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zennjero.kook.app.data.repository.PersonalDetailsRepository
import com.zennjero.kook.app.domain.PersonalDetails
import com.zennjero.kook.app.framework.network.Resource
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class PersonalDetailsViewModel :ViewModel() {
    var name = MutableLiveData<String>()
    var email = MutableLiveData<String>()
    var phonenumber = MutableLiveData<String>()
    var dob = MutableLiveData<String>()

    private val repository = PersonalDetailsRepository()
    fun savePersonalDetails(): MutableLiveData<Resource<Void?>> {
        //phonenumber.value = "9411426063" //This is for test purpose only
        val personalDetails = PersonalDetails(name.value!!,dob.value!!,email.value!!,
        phonenumber.value!!)
        val result = MutableLiveData<Resource<Void?>>()

        viewModelScope.launch {
            repository.savePersonalDetails(personalDetails).collect { resource ->
                result.postValue(resource)
            }
        }
        return result
    }
}