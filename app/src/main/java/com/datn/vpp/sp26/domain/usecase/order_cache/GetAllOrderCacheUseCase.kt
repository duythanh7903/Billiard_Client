package com.datn.vpp.sp26.domain.usecase.order_cache

import com.datn.vpp.sp26.domain.repository.OrderCacheRepository
import javax.inject.Inject

class GetAllOrderCacheUseCase @Inject constructor(
    private val orderCacheRepository: OrderCacheRepository
) {
    operator fun invoke() = orderCacheRepository.getAllCacheOrder()
}