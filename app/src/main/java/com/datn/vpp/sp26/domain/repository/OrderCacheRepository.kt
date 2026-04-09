package com.datn.vpp.sp26.domain.repository

import com.datn.vpp.sp26.domain.model.entity.OrderEntity
import kotlinx.coroutines.flow.Flow

interface OrderCacheRepository {
    suspend fun cacheListOrder(list: List<OrderEntity>)

    fun getAllCacheOrder(): Flow<List<OrderEntity>>

    suspend fun clearCacheOrder()

    suspend fun deleteOrderById(id: String)
}