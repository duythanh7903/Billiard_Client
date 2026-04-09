package com.datn.vpp.sp26.domain.usecase.cat_cache

import com.datn.vpp.sp26.domain.repository.CategoryCacheRepository
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ClearCacheCatUseCase @Inject constructor(
    private val categoryCacheRepository: CategoryCacheRepository
) {
    operator fun invoke() = flow {
        emit(categoryCacheRepository.clearAllCacheListCategory())
    }
}