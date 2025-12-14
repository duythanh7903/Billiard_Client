package com.datn.bia.a.domain.repository

import com.datn.bia.a.data.network.factory.ResultWrapper
import com.datn.bia.a.domain.model.dto.req.ReqCancelOrder
import com.datn.bia.a.domain.model.dto.req.ReqCheckOutDTO
import com.datn.bia.a.domain.model.dto.req.ReqUpdateOrder
import com.datn.bia.a.domain.model.dto.res.ResAllOrder
import com.datn.bia.a.domain.model.dto.res.ResCheckOutDTO
import com.datn.bia.a.domain.model.dto.res.ResOrderDTO
import com.datn.bia.a.domain.model.dto.res.ResUpdateOrder
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