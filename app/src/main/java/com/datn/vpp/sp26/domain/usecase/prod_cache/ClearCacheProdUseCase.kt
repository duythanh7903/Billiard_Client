package com.datn.vpp.sp26.domain.usecase.prod_cache

import com.datn.vpp.sp26.domain.repository.ProductCacheRepository
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ClearCacheProdUseCase @Inject constructor(
    private val productCacheRepository: ProductCacheRepository
) {
    operator fun invoke() = flow {
        emit(productCacheRepository.clearAllCacheListProduct())
    }
}