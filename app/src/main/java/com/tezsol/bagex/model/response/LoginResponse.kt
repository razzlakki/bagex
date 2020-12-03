package com.tezsol.bagex.model.response

data class LoginResponse(
    val code: Int,
    val `data`: List<Data>,
    val message: String,
    val request: String,
    val token: String
)

data class Data(
    val countryCode: String,
    val deviceId: String,
    val dialingCode: String,
    val email: String,
    val fcmToken: String,
    val name: String,
    val osName: String,
    val osVersion: String,
    val phone: String,
    val roles: List<String>,
    val token: String,
    val userId: String,
    val userName: String
)