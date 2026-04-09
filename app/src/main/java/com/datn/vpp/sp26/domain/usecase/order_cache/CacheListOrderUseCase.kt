package com.datn.vpp.sp26.domain.usecase.order_cache

import com.datn.vpp.sp26.domain.model.entity.OrderEntity
import com.datn.vpp.sp26.domain.repository.OrderCacheRepository
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class CacheListOrderUseCase @Inject constructor(
    private val orderCacheRepository: OrderCacheRepository
) {
    operator fun invoke(list: List<OrderEntity>) = flow {
        emit(orderCacheRepository.cacheListOrder(list))
    }
}