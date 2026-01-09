package com.datn.bia.a.data.database.repository

import com.datn.bia.a.data.database.dao.FeedBackDao
import com.datn.bia.a.domain.model.entity.FeedbackEntity
import com.datn.bia.a.domain.repository.FeedbackRepository
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