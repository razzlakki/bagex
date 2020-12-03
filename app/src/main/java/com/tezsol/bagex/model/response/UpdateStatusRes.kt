package com.tezsol.bagex.model.response

data class UpdateStatusRes(
    val code: Int,
    val message: String,
    val token: String,
    val data: List<String>,
    val request: String,
    val errors: Errors
)

data class Errors(

    val errortoken: String,
    val errordetails: List<Errordetails>
)

data class Errordetails(

    val code: String,
    val description: String
)