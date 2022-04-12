package com.zennjero.kook.app.data.repository

import com.zennjero.kook.app.framework.UserFcmTokenDataSourceImpl
import com.zennjero.kook.app.framework.network.Resource
import kotlinx.coroutines.flow.Flow

class UserFcmTokenRepository {

    private val userFcmTokenDataSource = UserFcmTokenDataSourceImpl()

    fun getToken(userId:String): Flow<Resource<String?>>{
        return userFcmTokenDataSource.getToken(userId)
    }

    fun saveToken(token:String): Flow<Resource<Void?>>{
        return userFcmTokenDataSource.saveToken(token)
    }

}