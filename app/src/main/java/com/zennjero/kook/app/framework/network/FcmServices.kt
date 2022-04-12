package com.zennjero.kook.app.framework.network

import android.util.Log
import com.zennjero.kook.app.domain.notification.PushNotification
import com.zennjero.kook.app.presentation.util.Constant
import kotlinx.coroutines.flow.flow
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class FcmServices {

    private val TAG = FcmServices::class.java.simpleName

    private val fcmAPI = Retrofit.Builder()
        .baseUrl(Constant.FCM_BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(FcmAPI::class.java)

    fun sendMessage(notification: PushNotification) = flow<Resource<Void?>>{
        Log.d(TAG, "Notification sending...")
        val response = fcmAPI.sendMessage(notification)
        if(response.isSuccessful){
            Log.d(TAG, "Notification sent")
            emit(Resource.success(null))
        }else{
            Log.d(TAG, "Failed to send notification")
            emit(Resource.error(null, Constant.DEFAULT_ERROR))
        }
    }
}