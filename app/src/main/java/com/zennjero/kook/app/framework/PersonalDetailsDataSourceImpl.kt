package com.zennjero.kook.app.framework

import com.zennjero.kook.app.data.datasource.PersonalDetailsDataSource
import com.zennjero.kook.app.domain.PersonalDetails
import com.zennjero.kook.app.framework.network.PersonalDetailsAPI
import com.zennjero.kook.app.framework.network.Resource
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow

@ExperimentalCoroutinesApi
class PersonalDetailsDataSourceImpl : PersonalDetailsDataSource {

    private val personalDetailsAPI = PersonalDetailsAPI()

    override fun savePersonalDetails(personalDetails: PersonalDetails): Flow<Resource<Void?>> {
        return personalDetailsAPI.savePersonalDetails(personalDetails)
    }
}