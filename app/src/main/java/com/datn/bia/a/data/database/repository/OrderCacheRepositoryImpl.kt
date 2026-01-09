package com.datn.bia.a.data.database.repository

import com.datn.bia.a.data.database.dao.OrderDao
import com.datn.bia.a.domain.model.entity.OrderEntity
import com.datn.bia.a.domain.repository.OrderCacheRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class OrderCacheRepositoryImpl @Inject constructor(
    private val orderDao: OrderDao
) : OrderCacheRepository {
    override suspend fun cacheListOrder(list: List<OrderEntity>) =
        orderDao.cacheListOrder(list)

    override fun getAllCacheOrder(): Flow<List<OrderEntity>> =
        orderDao.getAllCacheOrder()

    override suspend fun clearCacheOrder() =
        orderDao.clearCacheOrder()
}