package com.zennjero.kook.app.framework.network

import com.zennjero.kook.app.domain.PersonalDetails
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow

class PersonalDetailsAPI : BaseAPI() {
    /**
     * This method will save the address to the data base in the address collection
     * and It will return a Flow<Boolean> will send true if it is success else false
     *
     * @param personalDetails
     * @return Flow<Resource<Void?>
     */

    @ExperimentalCoroutinesApi
    fun savePersonalDetails(personalDetails: PersonalDetails) = callbackFlow<Resource<Void?>> {
        FirebaseAuth.getInstance().uid?.let {
            saveDocument(
                "personaldetails",
                it,
                personalDetails,
                {
                    trySend(Resource.success(null))
                },
                {
                    trySend(Resource.error(null, it.localizedMessage ?: "Something went wrong"))
                }
            )
        }

        awaitClose {}
    }
}