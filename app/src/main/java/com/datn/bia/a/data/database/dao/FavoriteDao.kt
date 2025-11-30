package com.datn.bia.a.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.datn.bia.a.domain.model.entity.FavoriteEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteDao {
    @Insert
    suspend fun insertFavorite(entity: FavoriteEntity)

    @Query("delete from favorite_entity where productId like :id")
    suspend fun deleteFavoriteByProductId(id: String)

    @Query("select * from favorite_entity where productId like :id limit 1")
    suspend fun searchFavoriteByProductId(id: String): FavoriteEntity?

    @Query("select * from favorite_entity")
    fun getAllFavorite(): Flow<List<FavoriteEntity>>

}