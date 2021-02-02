package com.tezsol.bagex.api

import com.tezsol.bagex.model.request.GetOrdersReq
import com.tezsol.bagex.model.request.LoginReq
import com.tezsol.bagex.model.request.PackagePricingReq
import com.tezsol.bagex.model.request.UpdateScanStatusReq
import com.tezsol.bagex.model.response.LoginResponse
import com.tezsol.bagex.model.response.OrdersRes
import com.tezsol.bagex.model.response.PackagePriceRes
import com.tezsol.bagex.model.response.UpdateStatusRes
import io.reactivex.Observable
import retrofit2.http.*
import java.net.PasswordAuthentication

interface APIService {

    @Headers("api-version: 1")
    @POST("Account/Login")
    fun login(
        @Body loginReq: LoginReq
    ): Observable<LoginResponse>


    @Headers("api-version: 1")
    @POST("Account/Login")
    fun getAuthenticationToken(@Body loginReq: LoginReq): retrofit2.Call<LoginResponse>

    @Headers(
        "api-version: 1",
        "Content-Type: application/json"
    )
    @POST("Orders/GetOrders")
    fun getDeliveryJobs(
        @Body ordersReq: GetOrdersReq
    ): Observable<OrdersRes>


    @POST("Tracking/UpdateBagStatus")
    fun updateServicesStatus(
        @Body scanReq: UpdateScanStatusReq
    ): Observable<UpdateStatusRes>


    @Headers(
        "api-version: 1",
        "Content-Type: application/json"
    )
    @GET("Orders/GetOrderByAWBNo")
    fun getOrderByAWBNo(
        @Query("awbNo") awbNo: Long
    ): Observable<OrdersRes>


    @Headers(
        "api-version: 1",
        "Content-Type: application/json"
    )
    @POST("/Master/GetPackagePricing")
    fun getPackagePricing(
        @Body packReq: PackagePricingReq
    ): Observable<PackagePriceRes>


}