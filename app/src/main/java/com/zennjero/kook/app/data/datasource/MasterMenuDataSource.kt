package com.zennjero.kook.app.data.datasource

import com.zennjero.kook.app.domain.MasterMenuList
import com.zennjero.kook.app.framework.network.Resource
import kotlinx.coroutines.flow.Flow

interface MasterMenuDataSource {
    fun getmasterMenu() : Flow<Resource< MasterMenuList?>>
}