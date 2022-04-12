package com.zennjero.kook.app.framework

import com.zennjero.kook.app.data.datasource.AddressDataSource
import com.zennjero.kook.app.domain.Address
import com.zennjero.kook.app.framework.network.AddressAPI
import com.zennjero.kook.app.framework.network.Resource
import kotlinx.coroutines.flow.Flow

class AddressDataSourceImpl : AddressDataSource {

    private val addressAPI = AddressAPI()

    override fun saveAddress(address: Address): Flow<Resource<Void?>> {
        return addressAPI.saveAddress(address)
    }

}