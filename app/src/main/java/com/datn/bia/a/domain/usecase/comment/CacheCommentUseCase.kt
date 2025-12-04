package com.datn.bia.a.domain.usecase.comment

import com.datn.bia.a.domain.model.entity.CommentEntity
import com.datn.bia.a.domain.repository.CommentCacheRepository
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class CacheCommentUseCase @Inject constructor(
    private val commentCacheRepository: CommentCacheRepository
) {
    operator fun invoke(entity: CommentEntity) = flow {
        emit(commentCacheRepository.insertComment(entity))
    }
}