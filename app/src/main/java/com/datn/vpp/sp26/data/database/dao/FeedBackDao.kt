package com.datn.vpp.sp26.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.datn.vpp.sp26.domain.model.entity.FeedbackEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FeedBackDao {
    @Insert(entity = FeedbackEntity::class, onConflict = OnConflictStrategy.REPLACE)
    suspend fun cacheListFeedBack(list: List<FeedbackEntity>)

    @Query("delete from feedbackentity")
    suspend fun clearCacheFeedBack()

    @Query("select * from feedbackentity")
    fun getListFeedBack(): Flow<List<FeedbackEntity>>
}