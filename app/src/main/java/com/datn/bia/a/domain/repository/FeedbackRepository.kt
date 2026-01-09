package com.datn.bia.a.domain.repository

import com.datn.bia.a.domain.model.entity.FeedbackEntity
import kotlinx.coroutines.flow.Flow

interface FeedbackRepository {
    suspend fun cacheListFeedBack(list: List<FeedbackEntity>)

    suspend fun clearCacheFeedBack()

    fun getListFeedBack(): Flow<List<FeedbackEntity>>
}