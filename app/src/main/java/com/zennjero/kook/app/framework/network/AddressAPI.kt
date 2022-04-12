package com.zennjero.kook.app.framework.network

import com.zennjero.kook.app.domain.Address
import com.zennjero.kook.app.presentation.util.Constant
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow

class AddressAPI : BaseAPI(){

    /**
     * This method will save the address to the data base in the address collection
     * and It will return a Flow<Resource<Void?>>
     *
     * @param address
     * @return Flow<Resource<Void?>>
     */

    @ExperimentalCoroutinesApi
    fun saveAddress(address: Address) = callbackFlow<Resource<Void?>> {
        val uid = FirebaseAuth.getInstance().uid
        if(uid == null){
            // User is not signed in
            trySend(Resource.error(null, Constant.SIGNED_OUT_ERROR))
        }else{
            saveDocument(
                Constant.COLLECTION_ADDRESS,
                uid,
                address,
                {
                    trySend(Resource.success(null))
                },
                {
                    trySend(Resource.error(null, it.localizedMessage ?: Constant.DEFAULT_ERROR))
                }
            )
        }

        awaitClose {}
    }

}