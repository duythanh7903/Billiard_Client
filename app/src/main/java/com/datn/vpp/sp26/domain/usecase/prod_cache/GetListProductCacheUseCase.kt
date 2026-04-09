package com.datn.vpp.sp26.domain.usecase.prod_cache

import com.datn.vpp.sp26.domain.repository.ProductCacheRepository
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetListProductCacheUseCase @Inject constructor(
    private val productCacheRepository: ProductCacheRepository
) {
    operator fun invoke() = productCacheRepository.getListProduct()
}