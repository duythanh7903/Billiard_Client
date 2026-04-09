package com.datn.vpp.sp26.domain.usecase.order_cache

import com.datn.vpp.sp26.domain.repository.OrderCacheRepository
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class DeleteOrderByIdUseCase @Inject constructor(
    private val orderCacheRepository: OrderCacheRepository
) {
    operator fun invoke(id: String) = flow {
        orderCacheRepository.deleteOrderById(id)

        emit(true)
    }
}