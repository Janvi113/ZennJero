package com.zennjero.kook.app.components

import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.zennjero.kook.app.data.repository.UserFcmTokenRepository
import com.zennjero.kook.app.framework.network.Status
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.runBlocking

class FirebaseCloudMessagingServices:FirebaseMessagingService() {

    private val TAG = FirebaseCloudMessagingServices::class.java.simpleName
    private val userFcmTokenRepository = UserFcmTokenRepository()

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        Log.d(TAG, "New token is $token")

        // save token to fire-store
        runBlocking{
            userFcmTokenRepository.saveToken(token)
                .collect {
                    if(it.status == Status.SUCCESS){
                        Log.d(TAG, "Token saved to fire-store")
                    }else{
                        Log.d(TAG, "Failed to save token to fire-store")
                    }
                }
        }
    }

    override fun onMessageReceived(p0: RemoteMessage) {
        super.onMessageReceived(p0)
        Log.d(TAG, "Message received ${p0.data}")
    }

}