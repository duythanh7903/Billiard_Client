package com.datn.vpp.sp26.data.network.repository

import com.datn.vpp.sp26.data.network.factory.ResultWrapper
import com.datn.vpp.sp26.data.network.service.CommentService
import com.datn.vpp.sp26.domain.model.dto.req.ReqCommentDTO
import com.datn.vpp.sp26.domain.model.dto.res.ResCommentDTO
import com.datn.vpp.sp26.domain.repository.CommentRepository
import javax.inject.Inject

class CommentRepositoryImpl @Inject constructor(
    private val commentService: CommentService
): CommentRepository {
    override suspend fun createComment(req: ReqCommentDTO): ResultWrapper<Any> =
        commentService.createComment(req)

    override suspend fun getComment(): ResultWrapper<List<ResCommentDTO>> =
        commentService.getComment()
}