package com.datn.vpp.sp26.domain.repository

import com.datn.vpp.sp26.domain.model.entity.FeedbackEntity
import kotlinx.coroutines.flow.Flow

interface FeedbackRepository {
    suspend fun cacheListFeedBack(list: List<FeedbackEntity>)

    suspend fun clearCacheFeedBack()

    fun getListFeedBack(): Flow<List<FeedbackEntity>>
}