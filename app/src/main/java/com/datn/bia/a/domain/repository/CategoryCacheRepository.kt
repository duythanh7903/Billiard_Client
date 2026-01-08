package com.datn.bia.a.domain.repository

import com.datn.bia.a.domain.model.entity.CategoryEntity
import kotlinx.coroutines.flow.Flow

interface CategoryCacheRepository {
    suspend fun cacheListCategory(list: List<CategoryEntity>)

    suspend fun clearAllCacheListCategory()

    fun getListCategory(): Flow<List<CategoryEntity>>
}