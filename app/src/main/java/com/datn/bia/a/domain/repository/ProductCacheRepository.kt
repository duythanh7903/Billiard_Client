package com.datn.bia.a.domain.repository

import com.datn.bia.a.domain.model.entity.ProductEntity
import kotlinx.coroutines.flow.Flow

interface ProductCacheRepository {
    suspend fun cacheListProduct(list: List<ProductEntity>)

    suspend fun clearAllCacheListProduct()

    fun getListProduct(): Flow<List<ProductEntity>>
}