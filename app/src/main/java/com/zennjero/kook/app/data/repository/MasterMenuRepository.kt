package com.zennjero.kook.app.data.repository

import com.zennjero.kook.app.domain.MasterMenuList
import com.zennjero.kook.app.framework.MasterMenuDataSourceImpl
import com.zennjero.kook.app.framework.network.Resource
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow

@ExperimentalCoroutinesApi
class MasterMenuRepository {

    private val masterMenuDataSourceImpl = MasterMenuDataSourceImpl()

    fun getMasterMenu(): Flow<Resource< MasterMenuList?>> = masterMenuDataSourceImpl.getmasterMenu()
}