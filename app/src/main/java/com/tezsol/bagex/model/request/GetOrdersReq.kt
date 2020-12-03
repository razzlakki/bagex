package com.tezsol.bagex.model.request

data class GetOrdersReq(
    val statuslist: Array<String>? = null,
    val awbno: Long? = null,
    val agentid: String? = null,
    val userid: String? = null,
    val departuretime: String? = null,
    val arrivaltime: String? = null,
    val slotdate: String? = null,
    val slottime: String? = null,
    val dialingcode: String? = null,
    val mobile: String? = null,
    val servicetype: String? = null,
    val pnrnumber: String? = null,
    val bagid: String? = null
)