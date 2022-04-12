package com.zennjero.kook.app.domain

data class Order(
    var _id:String = "",
    var cookId:String = "",
    var foodieId:String = "",
    var orderNo:Int = 0,
    var foodieName:String = "",
    var billAmount:Float = 0f,
    var status: OrderStatus = OrderStatus.ORDER_PLACED,
    var createdMillis:Long = 0,
    var updatedMillis:Long = 0,
    var dishes: MutableList<OrderItem> = mutableListOf()
)
