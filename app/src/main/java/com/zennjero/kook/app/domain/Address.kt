package com.zennjero.kook.app.domain

/**
 * This data class will contain all address related data of a user
 */

data class Address(
    var houseNo:Int = 0,
    var apartmentName: String? = null,
    var landmark: String? = null,
    var name: String? = null,
    var addressLine: String? = null,
    var location: Location? = null
)
