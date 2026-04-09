package com.datn.vpp.sp26.domain.repository

import com.datn.vpp.sp26.data.network.factory.ResultWrapper
import com.datn.vpp.sp26.domain.model.dto.req.ReqCancelOrder
import com.datn.vpp.sp26.domain.model.dto.req.ReqCheckOutDTO
import com.datn.vpp.sp26.domain.model.dto.req.ReqUpdateOrder
import com.datn.vpp.sp26.domain.model.dto.res.ResAllOrder
import com.datn.vpp.sp26.domain.model.dto.res.ResCheckOutDTO
import com.datn.vpp.sp26.domain.model.dto.res.ResOrderDTO
import com.datn.vpp.sp26.domain.model.dto.res.ResUpdateOrder
import retrofit2.http.Path

interface OrderRepository {
    suspend fun checkOut(
        reqCheckOutDTO: ReqCheckOutDTO
    ): ResultWrapper<ResCheckOutDTO>

    suspend fun getOrdersByUser(
        @Path("userId") userId: String
    ): ResultWrapper<List<ResOrderDTO>>

    suspend fun updateOrder(
        orderId: String,
        reqUpdateOrder: ReqUpdateOrder
    ): ResultWrapper<ResUpdateOrder>

    suspend fun cancelOrder(
        orderId: String,
        reqUpdateOrder: ReqCancelOrder
    ): ResultWrapper<ResUpdateOrder>

    suspend fun getAllOrder(): ResultWrapper<ResAllOrder>
}