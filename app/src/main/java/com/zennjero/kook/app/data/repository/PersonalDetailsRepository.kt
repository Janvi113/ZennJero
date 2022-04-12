package com.zennjero.kook.app.data.repository

import com.zennjero.kook.app.domain.PersonalDetails
import com.zennjero.kook.app.framework.PersonalDetailsDataSourceImpl
import com.zennjero.kook.app.framework.network.Resource
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow

@ExperimentalCoroutinesApi
class PersonalDetailsRepository {
    private val personalDetailsDataSourceImpl = PersonalDetailsDataSourceImpl()

    fun savePersonalDetails(personalDetails: PersonalDetails):Flow<Resource<Void?>> =
        personalDetailsDataSourceImpl.savePersonalDetails(personalDetails)

}