package com.datn.vpp.sp26.data.database.repository

import com.datn.vpp.sp26.data.database.dao.CommentDao
import com.datn.vpp.sp26.domain.model.entity.CommentEntity
import com.datn.vpp.sp26.domain.repository.CommentCacheRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CommentCacheRepositoryImpl @Inject constructor(
    private val commentDao: CommentDao
) : CommentCacheRepository {
    override suspend fun insertComment(commentEntity: CommentEntity) =
        commentDao.insertComment(commentEntity)

    override fun getAllCommentBy(id: String): Flow<List<CommentEntity>> =
        commentDao.getAllCommentBy(id)
}