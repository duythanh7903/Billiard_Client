package com.datn.bia.a.domain.repository

import com.datn.bia.a.domain.model.entity.CommentEntity
import kotlinx.coroutines.flow.Flow

interface CommentCacheRepository {
    suspend fun insertComment(commentEntity: CommentEntity)
    fun getAllCommentBy(id: String): Flow<List<CommentEntity>>
}