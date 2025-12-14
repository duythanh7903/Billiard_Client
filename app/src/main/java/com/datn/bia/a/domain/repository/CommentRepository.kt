package com.datn.bia.a.domain.repository

import com.datn.bia.a.data.network.factory.ResultWrapper
import com.datn.bia.a.domain.model.dto.req.ReqCommentDTO
import com.datn.bia.a.domain.model.dto.res.ResCommentDTO

interface CommentRepository {
    suspend fun createComment(req: ReqCommentDTO): ResultWrapper<Any>
    suspend fun getComment(): ResultWrapper<List<ResCommentDTO>>
}