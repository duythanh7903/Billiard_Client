package com.datn.bia.a.data.database.repository

import com.datn.bia.a.data.database.dao.ProductDao
import com.datn.bia.a.domain.model.entity.ProductEntity
import com.datn.bia.a.domain.repository.ProductCacheRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ProductCacheRepositoryImpl @Inject constructor(
    private val productDao: ProductDao
) : ProductCacheRepository {
    override suspend fun cacheListProduct(list: List<ProductEntity>) =
        productDao.cacheListProduct(list)

    override suspend fun clearAllCacheListProduct() =
        productDao.clearAllCacheListProduct()

    override fun getListProduct(): Flow<List<ProductEntity>> =
        productDao.getListProduct()
}