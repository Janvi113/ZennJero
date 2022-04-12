package com.zennjero.kook.app.framework

import com.zennjero.kook.app.data.datasource.OrderDataSource
import com.zennjero.kook.app.domain.Order
import com.zennjero.kook.app.domain.OrderStatus
import com.zennjero.kook.app.framework.network.OrderAPI
import com.zennjero.kook.app.framework.network.Resource
import kotlinx.coroutines.flow.Flow
import java.util.*

class OrderDataSourceImpl : OrderDataSource {

    private val orderAPI = OrderAPI()

    override fun updateStatus(orderId: String, status: OrderStatus): Flow<Resource<Void?>> {
        return orderAPI.updateStatus(orderId, status)
    }

    override fun getOrders(date: Date, kookId: String, statusFilters: List<OrderStatus>?): Flow<Resource<List<Order>?>> {
        return orderAPI.getOrders(date, kookId, statusFilters)
    }
}