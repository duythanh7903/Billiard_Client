package com.datn.bia.a.data.network.repository

import com.datn.bia.a.data.network.factory.ResultWrapper
import com.datn.bia.a.data.network.service.OrderService
import com.datn.bia.a.domain.model.dto.req.ReqCancelOrder
import com.datn.bia.a.domain.model.dto.req.ReqCheckOutDTO
import com.datn.bia.a.domain.model.dto.req.ReqUpdateOrder
import com.datn.bia.a.domain.model.dto.res.ResAllOrder
import com.datn.bia.a.domain.model.dto.res.ResCheckOutDTO
import com.datn.bia.a.domain.model.dto.res.ResOrderDTO
import com.datn.bia.a.domain.model.dto.res.ResUpdateOrder
import com.datn.bia.a.domain.repository.OrderRepository
import javax.inject.Inject

class OrderRepositoryImpl @Inject constructor(
    private val orderService: OrderService
) : OrderRepository {
    override suspend fun checkOut(reqCheckOutDTO: ReqCheckOutDTO): ResultWrapper<ResCheckOutDTO> =
        orderService.checkOut(reqCheckOutDTO)

    override suspend fun getOrdersByUser(userId: String): ResultWrapper<List<ResOrderDTO>> =
        orderService.getOrdersByUser(userId)

    override suspend fun updateOrder(
        orderId: String,
        reqUpdateOrder: ReqUpdateOrder
    ): ResultWrapper<ResUpdateOrder> = orderService.updateOrder(
        orderId,
        reqUpdateOrder
    )

    override suspend fun cancelOrder(
        orderId: String,
        reqUpdateOrder: ReqCancelOrder
    ): ResultWrapper<ResUpdateOrder> =
        orderService.cancelOrder(orderId, reqUpdateOrder)

    override suspend fun getAllOrder(): ResultWrapper<ResAllOrder> =
        orderService.getAllOrder()
}