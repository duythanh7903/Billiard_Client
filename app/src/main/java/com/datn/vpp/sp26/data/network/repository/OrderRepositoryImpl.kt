package com.datn.vpp.sp26.data.network.repository

import com.datn.vpp.sp26.data.network.factory.ResultWrapper
import com.datn.vpp.sp26.data.network.service.OrderService
import com.datn.vpp.sp26.domain.model.dto.req.ReqCancelOrder
import com.datn.vpp.sp26.domain.model.dto.req.ReqCheckOutDTO
import com.datn.vpp.sp26.domain.model.dto.req.ReqUpdateOrder
import com.datn.vpp.sp26.domain.model.dto.res.ResAllOrder
import com.datn.vpp.sp26.domain.model.dto.res.ResCheckOutDTO
import com.datn.vpp.sp26.domain.model.dto.res.ResOrderDTO
import com.datn.vpp.sp26.domain.model.dto.res.ResUpdateOrder
import com.datn.vpp.sp26.domain.repository.OrderRepository
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