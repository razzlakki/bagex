package com.tezsol.bagex.model.response

data class OrdersInfo(
    val awbNo: Long? = null,
    val transcId: String? = null,
    val tAirline: String? = null,
    val tService: String? = null,
    val tPupCity: String? = null,
    val tEmail: String? = null,
    val tPhone: String? = null,
    val countryCode: String? = null,
    val dialingCode: String? = null,
    val bagsNo: Int? = null,
    val flightNo: String? = null,
    val destCity: String? = null,
    val flightDate: String? = null,
    val flightTime: String? = null,
    val sLocation: String? = null,
    val landmark: String? = null,
    val spdDate: String? = null,
    val spdTime: String? = null,
    val tCharges: String? = null,
    val tvat: String? = null,
    val doc: String? = null,
    val tStatus: String? = null,
    val tpupdt: String? = null,
    val tDevlDT: String? = null,
    val tPromoCode: String? = null,
    val rComments: String? = null,
    val pnrNo: String? = null,
    val gpsPoints: String? = null,
    val imageUrls: List<String>? = null,
    val tStatusDescription: String? = null,
    val bagId: String? = null
)