package com.datn.vpp.sp26.domain.usecase.cat_cache

import com.datn.vpp.sp26.domain.repository.CategoryCacheRepository
import javax.inject.Inject

class GetListCatCacheUseCase @Inject constructor(
    private val categoryCacheRepository: CategoryCacheRepository
) {
    operator fun invoke() = categoryCacheRepository.getListCategory()
}