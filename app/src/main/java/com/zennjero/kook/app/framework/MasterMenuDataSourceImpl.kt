package com.zennjero.kook.app.framework

import com.zennjero.kook.app.data.datasource.MasterMenuDataSource
import com.zennjero.kook.app.domain.MasterMenuList
import com.zennjero.kook.app.framework.network.MasterMenuApi
import com.zennjero.kook.app.framework.network.Resource
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
@ExperimentalCoroutinesApi
class MasterMenuDataSourceImpl: MasterMenuDataSource {

    private val masterMenuApi = MasterMenuApi()

    override fun getmasterMenu(): Flow<Resource< MasterMenuList?>> {
        return masterMenuApi.getMenuDetails()
    }
}