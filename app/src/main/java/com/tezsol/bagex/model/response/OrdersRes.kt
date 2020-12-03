package com.tezsol.bagex.model.response

data class OrdersRes(
    val code: Int,
    val message: String,
    val token: String,
    val data: List<OrdersInfo>,
    val request: String
)