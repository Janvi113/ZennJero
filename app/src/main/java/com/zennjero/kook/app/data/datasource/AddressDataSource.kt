package com.zennjero.kook.app.data.datasource

import com.zennjero.kook.app.domain.Address
import com.zennjero.kook.app.framework.network.Resource
import kotlinx.coroutines.flow.Flow

interface AddressDataSource {
    fun saveAddress(address: Address):Flow<Resource<Void?>>
}