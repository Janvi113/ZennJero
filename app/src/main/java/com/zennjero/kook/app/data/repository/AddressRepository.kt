package com.zennjero.kook.app.data.repository

import com.zennjero.kook.app.domain.Address
import com.zennjero.kook.app.framework.AddressDataSourceImpl
import com.zennjero.kook.app.framework.network.Resource
import kotlinx.coroutines.flow.Flow

class AddressRepository {

    private val addressDataSource = AddressDataSourceImpl()

    fun saveAddress(address: Address): Flow<Resource<Void?>> {
        return addressDataSource.saveAddress(address)
    }
}