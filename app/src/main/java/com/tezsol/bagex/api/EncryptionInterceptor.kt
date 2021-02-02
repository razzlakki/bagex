package com.tezsol.bagex.api

import com.tezsol.bagex.sharedutil.SharedUtil
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.Response


class EncryptionInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        var request: Request = chain.request()
        val rawBody: RequestBody? = request.body()
        val token = SharedUtil.instance.getString(SharedUtil.LOGIN_TOKEN);
        request = request.newBuilder()
            .header("Content-Type", "application/json")
            .header("api-version", "1")
            .addHeader("Authorization", "Bearer $token")
            .method(request.method(), rawBody).build()

        return chain.proceed(request);
    }
}