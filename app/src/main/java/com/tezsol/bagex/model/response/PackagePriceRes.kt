package com.tezsol.bagex.model.response

data class PackagePriceRes(
    val code: Int,
    val `data`: List<PackageData>,
    val errors: Errors,
    val message: String,
    val request: String,
    val token: String
)

data class PackageData(
    val addlbag: Int,
    val bag: Int,
    val code: String,
    val destination: String,
    val name: String,
    val pickup: String,
    val vat: Float
)

data class Errordetail(
    val code: String,
    val description: String
)