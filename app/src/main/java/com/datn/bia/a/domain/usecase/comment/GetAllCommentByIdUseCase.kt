package com.datn.bia.a.domain.usecase.comment

import com.datn.bia.a.domain.repository.CommentCacheRepository
import javax.inject.Inject

class GetAllCommentByIdUseCase @Inject constructor(
    private val commentRepository: CommentCacheRepository
) {
    operator fun invoke(id: String) = commentRepository.getAllCommentBy(id)

}