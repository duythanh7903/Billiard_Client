package com.datn.bia.a.data.database.repository

import com.datn.bia.a.data.database.dao.FavoriteDao
import com.datn.bia.a.domain.model.entity.FavoriteEntity
import com.datn.bia.a.domain.repository.FavoriteRepository
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