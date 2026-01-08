package com.datn.bia.a.domain.usecase.prod_cache

import com.datn.bia.a.domain.repository.ProductCacheRepository
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ClearCacheProdUseCase @Inject constructor(
    private val productCacheRepository: ProductCacheRepository
) {
    operator fun invoke() = flow {
        emit(productCacheRepository.clearAllCacheListProduct())
    }
}