package com.datn.vpp.sp26.data.database.repository

import com.datn.vpp.sp26.data.database.dao.FavoriteDao
import com.datn.vpp.sp26.domain.model.entity.FavoriteEntity
import com.datn.vpp.sp26.domain.repository.FavoriteRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FavoriteRepositoryImpl @Inject constructor(
    private val favoriteDao: FavoriteDao
) : FavoriteRepository {
    override suspend fun insertFavorite(entity: FavoriteEntity) =
        favoriteDao.insertFavorite(entity)

    override suspend fun deleteFavoriteByProductId(id: String) =
        favoriteDao.deleteFavoriteByProductId(id)

    override suspend fun searchFavoriteByProductId(id: String): FavoriteEntity? =
        favoriteDao.searchFavoriteByProductId(id)

    override fun getAllFavorite(): Flow<List<FavoriteEntity>> =
        favoriteDao.getAllFavorite()

}