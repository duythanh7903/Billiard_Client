package com.datn.bia.a.domain.usecase.order_cache

import com.datn.bia.a.domain.model.entity.OrderEntity
import com.datn.bia.a.domain.repository.OrderCacheRepository
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class CacheListOrderUseCase @Inject constructor(
    private val orderCacheRepository: OrderCacheRepository
) {
    operator fun invoke(list: List<OrderEntity>) = flow {
        emit(orderCacheRepository.cacheListOrder(list))
    }
}