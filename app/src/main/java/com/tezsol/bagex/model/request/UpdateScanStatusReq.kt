package com.tezsol.bagex.model.request

data class UpdateScanStatusReq(
    val bdnos: Array<String>? = null,
    val status: String? = null,
)