package com.zennjero.kook.app.data.datasource

import com.zennjero.kook.app.domain.Order
import com.zennjero.kook.app.domain.OrderStatus
import com.zennjero.kook.app.framework.network.Resource
import kotlinx.coroutines.flow.Flow
import java.util.*

interface OrderDataSource {
    fun updateStatus(orderId:String, status: OrderStatus): Flow<Resource<Void?>>
    fun getOrders(date: Date, kookId:String, statusFilters: List<OrderStatus>? = null): Flow<Resource<List<Order>?>>
}