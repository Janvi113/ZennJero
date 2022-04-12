package com.zennjero.kook.app.framework.network

import com.zennjero.kook.app.domain.notification.PushNotification
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface FcmAPI {

    companion object{
        const val CONTENT_TYPE = "application/json"
        const val serverKey = "AAAAjGckoaI:APA91bFaXQz9CAi0-viPKEBBoVasZVhQKt2CGiHpcvyUfYzqGATDT0p4oIgytoLqJbsGKHU9nEBlnBO6VlrOpr7ATC5RyIWt5Iomq7ojO5tmToypJacgvdpYqd1BvlVnXTJiD6cMMZX_"
    }

    @Headers("Authorization: key=$serverKey", "Content-Type:$CONTENT_TYPE")
    @POST("fcm/send")
    suspend fun sendMessage(@Body notification: PushNotification): Response<Void>
}