package com.zennjero.kook.app.data.datasource

import com.zennjero.kook.app.domain.PersonalDetails
import com.zennjero.kook.app.framework.network.Resource
import kotlinx.coroutines.flow.Flow

interface PersonalDetailsDataSource {
    fun savePersonalDetails(personalDetails: PersonalDetails):Flow<Resource<Void?>>
}