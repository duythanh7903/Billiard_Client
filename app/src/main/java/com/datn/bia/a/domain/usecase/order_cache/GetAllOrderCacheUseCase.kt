package com.datn.bia.a.domain.usecase.order_cache

import com.datn.bia.a.domain.repository.OrderCacheRepository
import javax.inject.Inject

class GetAllOrderCacheUseCase @Inject constructor(
    private val orderCacheRepository: OrderCacheRepository
) {
    operator fun invoke() = orderCacheRepository.getAllCacheOrder()
}