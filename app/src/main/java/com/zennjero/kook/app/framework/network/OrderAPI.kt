package com.zennjero.kook.app.framework.network

import android.util.Log
import com.zennjero.kook.app.domain.Order
import com.zennjero.kook.app.domain.OrderStatus
import com.zennjero.kook.app.presentation.util.Constant
import com.zennjero.kook.app.presentation.util.Utils
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import java.util.*

class OrderAPI : BaseAPI() {

    companion object{
        const val FIELD_COOK_ID = "cookId"
        const val FIELD_FOODIE_ID = "foodieId"
        const val FIELD_CREATED_MILLIS = "createdMillis"
        const val FIELD_STATUS = "status"
    }

    private val TAG = OrderAPI::class.java.simpleName

    /**
     * This method will update the order status of the order with
     * given orderId
     */
    @ExperimentalCoroutinesApi
    fun updateStatus(orderId: String, status: OrderStatus) = callbackFlow<Resource<Void?>> {

        db.collection(Constant.COLLECTION_ORDERS)
            .document(orderId)
            .update(FIELD_STATUS, status)
            .addOnSuccessListener {
                trySend(Resource.success(null))
                Log.d(TAG, "Order status updated")
            }
            .addOnFailureListener {
                trySend(Resource.error(null, it.localizedMessage ?: Constant.DEFAULT_ERROR))
                Log.d(TAG, "Failed to update order status")
            }

        awaitClose {}
    }

    /**
     * This method will load all orders of the Kook
     * on a given date
     */
    @ExperimentalCoroutinesApi
    fun getOrders(date: Date, kookId:String, statusFilters: List<OrderStatus>? = null) = callbackFlow<Resource<List<Order>?>>{
        val range = Utils.dateToMillisRange(date)

        var query = db.collection(Constant.COLLECTION_ORDERS)
            .whereEqualTo(FIELD_COOK_ID, kookId)
            .whereGreaterThanOrEqualTo(FIELD_CREATED_MILLIS, range.first)
            .whereLessThan(FIELD_CREATED_MILLIS, range.second)

        if(!statusFilters.isNullOrEmpty()) {
            query = query.whereIn(FIELD_STATUS, statusFilters)
            Log.d(TAG, "Filters applied are $statusFilters")
        }

        query.get()
            .addOnSuccessListener {
                val orders = it.toObjects(Order::class.java)
                for (i in 0 until orders.size){
                    orders[i]._id = it.documents[i].id
                }
                trySend(
                    Resource.success(
                        orders
                    )
                )
                Log.d(TAG, "Order loaded of $date orders $orders")
            }
            .addOnFailureListener {
                trySend(Resource.error(null, it.localizedMessage ?: Constant.DEFAULT_ERROR))
                Log.d(TAG, "Failed to load orders")
                it.printStackTrace()
            }

        awaitClose {}
    }
}