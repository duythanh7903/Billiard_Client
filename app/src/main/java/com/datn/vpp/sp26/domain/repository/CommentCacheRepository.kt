package com.datn.vpp.sp26.domain.repository

import com.datn.vpp.sp26.domain.model.entity.CommentEntity
import kotlinx.coroutines.flow.Flow

interface CommentCacheRepository {
    suspend fun insertComment(commentEntity: CommentEntity)
    fun getAllCommentBy(id: String): Flow<List<CommentEntity>>
}