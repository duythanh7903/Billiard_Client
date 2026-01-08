package com.datn.bia.a.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.datn.bia.a.domain.model.entity.ProductEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductDao {
    @Insert(entity = ProductEntity::class, onConflict = OnConflictStrategy.REPLACE)
    suspend fun cacheListProduct(list: List<ProductEntity>)

    @Query("delete from productentity")
    suspend fun clearAllCacheListProduct()

    @Query("select * from productentity")
    fun getListProduct(): Flow<List<ProductEntity>>
}