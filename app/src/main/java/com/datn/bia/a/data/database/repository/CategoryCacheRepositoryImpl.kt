package com.datn.bia.a.data.database.repository

import com.datn.bia.a.data.database.dao.CategoryDao
import com.datn.bia.a.domain.model.entity.CategoryEntity
import com.datn.bia.a.domain.repository.CategoryCacheRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CategoryCacheRepositoryImpl @Inject constructor(
    private val categoryDao: CategoryDao
) : CategoryCacheRepository {
    override suspend fun cacheListCategory(list: List<CategoryEntity>) =
        categoryDao.cacheListCategory(list)

    override suspend fun clearAllCacheListCategory() =
        categoryDao.clearAllCacheListCategory()

    override fun getListCategory(): Flow<List<CategoryEntity>> =
        categoryDao.getListCategory()

}