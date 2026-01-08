package com.datn.bia.a.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.datn.bia.a.domain.model.entity.CategoryEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CategoryDao {
    @Insert(entity = CategoryEntity::class, onConflict = OnConflictStrategy.REPLACE)
    suspend fun cacheListCategory(list: List<CategoryEntity>)

    @Query("delete from categoryentity")
    suspend fun clearAllCacheListCategory()

    @Query("select * from categoryentity")
    fun getListCategory(): Flow<List<CategoryEntity>>

}