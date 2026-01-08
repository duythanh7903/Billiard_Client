package com.datn.bia.a.domain.usecase.cat_cache

import com.datn.bia.a.domain.repository.CategoryCacheRepository
import javax.inject.Inject

class GetListCatCacheUseCase @Inject constructor(
    private val categoryCacheRepository: CategoryCacheRepository
) {
    operator fun invoke() = categoryCacheRepository.getListCategory()
}