package com.datn.vpp.sp26.data.database.repository

import com.datn.vpp.sp26.data.database.dao.FeedBackDao
import com.datn.vpp.sp26.domain.model.entity.FeedbackEntity
import com.datn.vpp.sp26.domain.repository.FeedbackRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FeedbackRepositoryImpl @Inject constructor(
    private val feedbackDao: FeedBackDao
) : FeedbackRepository {
    override suspend fun cacheListFeedBack(list: List<FeedbackEntity>) =
        feedbackDao.cacheListFeedBack(list)

    override suspend fun clearCacheFeedBack() =
        feedbackDao.clearCacheFeedBack()

    override fun getListFeedBack(): Flow<List<FeedbackEntity>> =
        feedbackDao.getListFeedBack()
}