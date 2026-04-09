package com.datn.vpp.sp26.domain.repository

import com.datn.vpp.sp26.domain.model.entity.ProductEntity
import kotlinx.coroutines.flow.Flow

interface ProductCacheRepository {
    suspend fun cacheListProduct(list: List<ProductEntity>)

    suspend fun clearAllCacheListProduct()

    fun getListProduct(): Flow<List<ProductEntity>>
}