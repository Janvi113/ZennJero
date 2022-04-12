package com.zennjero.kook.app.domain

enum class OrderStatus {
    ORDER_PLACED, PREPARING, ON_THE_WAY, DELIVERED, CANCELLED, SCHEDULED;
    val nextStatus get() = when(this){
        ORDER_PLACED -> PREPARING
        PREPARING -> ON_THE_WAY
        ON_THE_WAY -> DELIVERED
        CANCELLED -> CANCELLED
        SCHEDULED -> SCHEDULED
        DELIVERED -> DELIVERED
    }

    val prevStatus get() = when(this){
        ORDER_PLACED -> ORDER_PLACED
        PREPARING -> ORDER_PLACED
        ON_THE_WAY -> PREPARING
        CANCELLED -> CANCELLED
        SCHEDULED -> SCHEDULED
        DELIVERED -> ON_THE_WAY
    }
}