package com.zennjero.kook.app.framework

import com.zennjero.kook.app.data.datasource.UserFcmTokenDataSource
import com.zennjero.kook.app.framework.network.Resource
import com.zennjero.kook.app.framework.network.UserFcmTokenAPI
import kotlinx.coroutines.flow.Flow

class UserFcmTokenDataSourceImpl: UserFcmTokenDataSource {

    private val userFcmTokenAPI = UserFcmTokenAPI()

    override fun getToken(userId: String): Flow<Resource<String?>> {
        return userFcmTokenAPI.getToken(userId)
    }

    override fun saveToken(token: String): Flow<Resource<Void?>> {
        return userFcmTokenAPI.saveToken(token)
    }
}