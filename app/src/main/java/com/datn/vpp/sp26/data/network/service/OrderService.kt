package com.datn.vpp.sp26.data.network.service

import com.datn.vpp.sp26.common.AppConst
import com.datn.vpp.sp26.data.network.factory.ResultWrapper
import com.datn.vpp.sp26.data.storage.SharedPrefCommon
import com.datn.vpp.sp26.domain.model.dto.req.ReqCancelOrder
import com.datn.vpp.sp26.domain.model.dto.req.ReqCheckOutDTO
import com.datn.vpp.sp26.domain.model.dto.req.ReqProdCheckOut
import com.datn.vpp.sp26.domain.model.dto.req.ReqUpdateOrder
import com.datn.vpp.sp26.domain.model.dto.res.ResAllOrder
import com.datn.vpp.sp26.domain.model.dto.res.ResCheckOutDTO
import com.datn.vpp.sp26.domain.model.dto.res.ResCheckStockDTO
import com.datn.vpp.sp26.domain.model.dto.res.ResOrderDTO
import com.datn.vpp.sp26.domain.model.dto.res.ResUpdateOrder
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path

interface OrderService {
    @POST("order")
    suspend fun checkOut(
        @Body reqCheckOutDTO: ReqCheckOutDTO,
        @Header("Authorization") token: String = "Bearer ${SharedPrefCommon.token}"
    ): ResultWrapper<ResCheckOutDTO>

    @GET("order/user/{userId}/{isBusiness}")
    suspend fun getOrdersByUser(
        @Path("userId") userId: String,
        @Path("isBusiness") isBusiness: Boolean = SharedPrefCommon.role == AppConst.ROLE_WHOLESALE,
        @Header("Authorization") token: String = "Bearer ${SharedPrefCommon.token}"
    ): ResultWrapper<List<ResOrderDTO>>

    @PATCH("order/{orderId}")
    suspend fun updateOrder(
        @Path("orderId") orderId: String,
        @Body reqUpdateOrder: ReqUpdateOrder,
        @Header("Authorization") token: String = "Bearer ${SharedPrefCommon.token}"
    ): ResultWrapper<ResUpdateOrder>

    @PATCH("order/{orderId}")
    suspend fun cancelOrder(
        @Path("orderId") orderId: String,
        @Body reqUpdateOrder: ReqCancelOrder,
        @Header("Authorization") token: String = "Bearer ${SharedPrefCommon.token}"
    ): ResultWrapper<ResUpdateOrder>

    @GET("/orders")
    suspend fun getAllOrder(
        @Header("Authorization") token: String = "Bearer ${SharedPrefCommon.token}"
    ): ResultWrapper<ResAllOrder>

    @POST("order/check-stock")
    suspend fun checkStock(
        @Body products: List<ReqProdCheckOut>,
        @Header("Authorization") token: String = "Bearer ${SharedPrefCommon.token}"
    ): ResultWrapper<ResCheckStockDTO>
}