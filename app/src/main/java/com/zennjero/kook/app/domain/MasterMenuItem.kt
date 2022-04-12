package com.zennjero.kook.app.domain

data class MasterMenuItem(
    val cusine: String= "",
    val description: String= "",
    val dishName:String="",
    val foodType:String="",
    val imageUrl:String="",
    val price:Int=0,
    val serving: Int=0,
    val spicyLevel:Int=0,
    val status: Boolean=false
)