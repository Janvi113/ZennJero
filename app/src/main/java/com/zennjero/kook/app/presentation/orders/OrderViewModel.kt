package com.zennjero.kook.app.presentation.orders

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.zennjero.kook.app.data.repository.OrderRepository
import com.zennjero.kook.app.data.repository.UserFcmTokenRepository
import com.zennjero.kook.app.domain.Order
import com.zennjero.kook.app.domain.OrderStatus
import com.zennjero.kook.app.domain.notification.PushNotification
import com.zennjero.kook.app.framework.network.FcmServices
import com.zennjero.kook.app.framework.network.Resource
import com.zennjero.kook.app.presentation.util.Constant
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.util.*

open class OrderViewModel : ViewModel() {

    private val orderRepository = OrderRepository()
    private val userFcmTokenRepository = UserFcmTokenRepository()
    private val fcmServices = FcmServices()
    val orders = MutableLiveData<List<Order>?>()
    val date = MutableLiveData<Date>()
    val filters = MutableLiveData<MutableList<OrderStatus>?>()

    fun updateStatus(
        foodieId: String,
        orderId: String,
        status: OrderStatus
    ): MutableLiveData<Resource<Void?>> {
        val result = MutableLiveData<Resource<Void?>>()

        viewModelScope.launch {
            orderRepository.updateStatus(
                orderId = orderId,
                status = status
            ).collect { resource ->
                result.postValue(resource)
                if (resource.isSuccessful) {
                    // notify foodie about status change
                    sendNotification(foodieId, orderId, status)
                }
            }
        }

        return result
    }

    private suspend fun sendNotification(foodieId: String, orderId: String, status: OrderStatus) {
        userFcmTokenRepository.getToken(foodieId)
            .collect {
                if (it.isSuccessful && !it.data.isNullOrBlank()) {
                    fcmServices.sendMessage(
                        PushNotification.orderStatusUpdate(
                            token = it.data!!,
                            orderId = orderId,
                            status = status
                        )
                    ).collect {

                    }
                }
            }
    }

    fun getOrders(): LiveData<Resource<Void?>> {

        val result = MutableLiveData<Resource<Void?>>()
        if (Firebase.auth.currentUser != null) {
            viewModelScope.launch {
                orderRepository.getOrders(
                    date = date.value!!,
                    kookId = Firebase.auth.currentUser!!.uid,
                    statusFilters = filters.value
                ).collect { resource ->
                    orders.value = resource.data
                    result.postValue(Resource.success(null))
                }
            }
        }
        else{
            result.postValue(Resource.error(null,Constant.SIGNED_OUT_ERROR))
        }
        return result

    }

}