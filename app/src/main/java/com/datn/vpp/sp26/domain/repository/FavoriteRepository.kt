package com.datn.vpp.sp26.domain.repository

import com.datn.vpp.sp26.domain.model.entity.FavoriteEntity
import kotlinx.coroutines.flow.Flow

interface FavoriteRepository {
    suspend fun insertFavorite(entity: FavoriteEntity)
    suspend fun deleteFavoriteByProductId(id: String)
    suspend fun searchFavoriteByProductId(id: String): FavoriteEntity?
    fun getAllFavorite(): Flow<List<FavoriteEntity>>
}