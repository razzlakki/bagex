package com.tezsol.bagex.api

import com.tezsol.bagex.constants.APIConstants
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import timber.log.Timber
import timber.log.Timber.DebugTree


class APIClient {
    companion object {
        fun create(): APIService {

            Timber.plant(DebugTree())

            // HttpLoggingInterceptor
            val httpLoggingInterceptor = HttpLoggingInterceptor { message: String? ->
                Timber.i(
                    message
                )
            }
            httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

            val okHttpClient = OkHttpClient()
                .newBuilder().addInterceptor(EncryptionInterceptor())
                .addInterceptor(httpLoggingInterceptor)
//                .authenticator(Authenticator())
                .build()

            val retrofit = Retrofit.Builder()
                .addCallAdapterFactory(
                    RxJava2CallAdapterFactory.create()
                ).client(okHttpClient)
                .addConverterFactory(
                    GsonConverterFactory.create()
                )
                .baseUrl(APIConstants.BASE_URL)
                .build()
            return retrofit.create(APIService::class.java)
        }
    }
}

