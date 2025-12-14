package com.datn.bia.a.data.network.service

import com.datn.bia.a.data.network.factory.ResultWrapper
import com.datn.bia.a.domain.model.dto.req.ReqCancelOrder
import com.datn.bia.a.domain.model.dto.req.ReqCheckOutDTO
import com.datn.bia.a.domain.model.dto.req.ReqUpdateOrder
import com.datn.bia.a.domain.model.dto.res.ResAllOrder
import com.datn.bia.a.domain.model.dto.res.ResCheckOutDTO
import com.datn.bia.a.domain.model.dto.res.ResOrderDTO
import com.datn.bia.a.domain.model.dto.res.ResUpdateOrder
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path

interface OrderService {
    @POST("order")
    suspend fun checkOut(
        @Body reqCheckOutDTO: ReqCheckOutDTO
    ): ResultWrapper<ResCheckOutDTO>

    @GET("order/user/{userId}")
    suspend fun getOrdersByUser(
        @Path("userId") userId: String
    ): ResultWrapper<List<ResOrderDTO>>

    @PATCH("order/{orderId}")
    suspend fun updateOrder(
        @Path("orderId") orderId: String,
        @Body reqUpdateOrder: ReqUpdateOrder
    ): ResultWrapper<ResUpdateOrder>

    @PATCH("order/{orderId}")
    suspend fun cancelOrder(
        @Path("orderId") orderId: String,
        @Body reqUpdateOrder: ReqCancelOrder
    ): ResultWrapper<ResUpdateOrder>

    @GET("/orders")
    suspend fun getAllOrder(): ResultWrapper<ResAllOrder>
}