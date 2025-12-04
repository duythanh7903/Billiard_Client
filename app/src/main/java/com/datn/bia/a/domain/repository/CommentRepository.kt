package com.datn.bia.a.domain.repository

import com.datn.bia.a.data.network.factory.ResultWrapper
import com.datn.bia.a.domain.model.dto.req.ReqCommentDTO

interface CommentRepository {
    suspend fun createComment(req: ReqCommentDTO): ResultWrapper<Any>
}