package com.datn.vpp.sp26.domain.repository

import com.datn.vpp.sp26.domain.model.entity.CategoryEntity
import kotlinx.coroutines.flow.Flow

interface CategoryCacheRepository {
    suspend fun cacheListCategory(list: List<CategoryEntity>)

    suspend fun clearAllCacheListCategory()

    fun getListCategory(): Flow<List<CategoryEntity>>
}