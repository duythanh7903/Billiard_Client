package com.datn.bia.a.domain.repository

import com.datn.bia.a.domain.model.entity.OrderEntity
import kotlinx.coroutines.flow.Flow

interface OrderCacheRepository {
    suspend fun cacheListOrder(list: List<OrderEntity>)

    fun getAllCacheOrder(): Flow<List<OrderEntity>>

    suspend fun clearCacheOrder()
}