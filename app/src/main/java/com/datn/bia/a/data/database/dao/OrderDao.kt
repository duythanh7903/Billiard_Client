package com.datn.bia.a.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.datn.bia.a.domain.model.entity.OrderEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface OrderDao {
    @Insert(entity = OrderEntity::class, onConflict = OnConflictStrategy.REPLACE)
    suspend fun cacheListOrder(list: List<OrderEntity>)

    @Query("select * from orderentity")
    fun getAllCacheOrder(): Flow<List<OrderEntity>>

    @Query("delete from orderentity")
    suspend fun clearCacheOrder()
}