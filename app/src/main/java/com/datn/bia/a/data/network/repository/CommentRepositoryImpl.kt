package com.datn.bia.a.data.network.repository

import com.datn.bia.a.data.network.factory.ResultWrapper
import com.datn.bia.a.data.network.service.CommentService
import com.datn.bia.a.domain.model.dto.req.ReqCommentDTO
import com.datn.bia.a.domain.model.dto.res.ResCommentDTO
import com.datn.bia.a.domain.repository.CommentRepository
import javax.inject.Inject

class CommentRepositoryImpl @Inject constructor(
    private val commentService: CommentService
): CommentRepository {
    override suspend fun createComment(req: ReqCommentDTO): ResultWrapper<Any> =
        commentService.createComment(req)

    override suspend fun getComment(): ResultWrapper<List<ResCommentDTO>> =
        commentService.getComment()
}