package com.datn.bia.a.domain.repository

import com.datn.bia.a.domain.model.entity.FavoriteEntity
import kotlinx.coroutines.flow.Flow

interface FavoriteRepository {
    suspend fun insertFavorite(entity: FavoriteEntity)
    suspend fun deleteFavoriteByProductId(id: String)
    suspend fun searchFavoriteByProductId(id: String): FavoriteEntity?
    fun getAllFavorite(): Flow<List<FavoriteEntity>>
}