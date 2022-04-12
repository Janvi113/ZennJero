package com.zennjero.kook.app.framework.network

import com.google.firebase.auth.FirebaseAuth
import com.zennjero.kook.app.domain.MasterMenuList
import com.zennjero.kook.app.presentation.util.Constant
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow

class MasterMenuApi : BaseAPI() {
    @ExperimentalCoroutinesApi
    fun getMenuDetails() = callbackFlow<Resource<MasterMenuList?>> {
        FirebaseAuth.getInstance().uid?.let { id ->
            readDocument(
                "masterMenu",
                id,
                {
                    trySend(Resource.success(it?.toObject(MasterMenuList::class.java)))
                },
                {
                    trySend(Resource.error(null, it.localizedMessage?: Constant.DEFAULT_ERROR))
                }
            )
        }

        awaitClose {}
    }
}