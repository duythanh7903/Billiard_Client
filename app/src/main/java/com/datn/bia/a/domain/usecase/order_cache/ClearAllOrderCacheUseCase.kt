package com.datn.bia.a.domain.usecase.order_cache

import com.datn.bia.a.domain.repository.OrderCacheRepository
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ClearAllOrderCacheUseCase @Inject constructor(
    private val orderCacheRepository: OrderCacheRepository
) {
    operator fun invoke() = flow {
        emit(orderCacheRepository.clearCacheOrder())
    }
}