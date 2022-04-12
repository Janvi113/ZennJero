package com.zennjero.kook.app.data.repository

import com.zennjero.kook.app.domain.Order
import com.zennjero.kook.app.domain.OrderStatus
import com.zennjero.kook.app.framework.OrderDataSourceImpl
import com.zennjero.kook.app.framework.network.Resource
import kotlinx.coroutines.flow.Flow
import java.util.*

class OrderRepository {

    private val orderDataSource = OrderDataSourceImpl()

    fun updateStatus(orderId: String, status: OrderStatus): Flow<Resource<Void?>> {
        return orderDataSource.updateStatus(orderId, status)
    }

    fun getOrders(date: Date, kookId:String, statusFilters: List<OrderStatus>? = null): Flow<Resource<List<Order>?>>{
        return orderDataSource.getOrders(date, kookId, statusFilters)
    }
}