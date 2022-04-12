package com.zennjero.kook.app.framework.network

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.zennjero.kook.app.presentation.util.Constant
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow

class UserFcmTokenAPI : BaseAPI() {

    private val token_var_name = "token"
    val TAG = BaseAPI::class.java.simpleName

    @ExperimentalCoroutinesApi
    fun getToken(userId:String) = callbackFlow<Resource<String?>> {
        Log.d(TAG, "Get Token for user id $userId")
        readDocument(
            Constant.COLLECTION_USER_TOKENS,
            userId,
            {
                trySend(Resource.success(it?.data?.get(token_var_name) as String))
            },
            {
                trySend(Resource.error(null, it.localizedMessage?:Constant.DEFAULT_ERROR))
            }
        )

        awaitClose {  }
    }

    @ExperimentalCoroutinesApi
    fun saveToken(token:String) = callbackFlow<Resource<Void?>> {
        val uid = FirebaseAuth.getInstance().uid
        if(uid == null){
            // User is not signed in
            trySend(Resource.error(null, Constant.SIGNED_OUT_ERROR))
        }else {
            saveDocument(
                Constant.COLLECTION_USER_TOKENS,
                uid,
                mapOf(token_var_name to token),
                {
                    trySend(Resource.success(null))
                },
                {
                    trySend(Resource.error(null, it.localizedMessage ?: Constant.DEFAULT_ERROR))
                }
            )
        }

        awaitClose {  }
    }
}