package com.zennjero.kook.app.data.datasource

import com.zennjero.kook.app.framework.network.Resource
import kotlinx.coroutines.flow.Flow


interface UserFcmTokenDataSource {

    fun getToken(userId:String): Flow<Resource<String?>>
    fun saveToken(token:String): Flow<Resource<Void?>>

}