package com.datn.vpp.sp26.domain.usecase.comment

import com.datn.vpp.sp26.domain.repository.CommentCacheRepository
import javax.inject.Inject

class GetAllCommentByIdUseCase @Inject constructor(
    private val commentRepository: CommentCacheRepository
) {
    operator fun invoke(id: String) = commentRepository.getAllCommentBy(id)

}