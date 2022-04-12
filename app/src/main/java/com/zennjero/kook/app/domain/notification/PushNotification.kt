package com.zennjero.kook.app.domain.notification

import android.util.Log
import com.zennjero.kook.app.domain.OrderStatus

data class PushNotification(
    val data: Map<String, Any>,
    val notification: Notification?,
    var to: String
){
    companion object{
        const val TYPE_ORDER_STATUS_UPDATE = 1
        const val PARAM_ORDER_ID = "orderId"
        const val PARAM_STATUS = "status"
        const val PARAM_NOTIFICATION_TYPE = "type"
        val TAG = this::class.java.simpleName

        fun orderStatusUpdate(
            token:String,
            orderId:String,
            status: OrderStatus
        ):PushNotification{
            val data:Map<String, Any> = mapOf(
                PARAM_ORDER_ID to orderId,
                PARAM_STATUS to status,
                PARAM_NOTIFICATION_TYPE to TYPE_ORDER_STATUS_UPDATE
            )
            val pushNotification = PushNotification(data, null, "/token/$token")
            Log.d(TAG, "Created $pushNotification")
            return pushNotification
        }
    }
}