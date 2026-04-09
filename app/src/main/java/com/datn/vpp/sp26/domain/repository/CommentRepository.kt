package com.datn.vpp.sp26.domain.repository

import com.datn.vpp.sp26.data.network.factory.ResultWrapper
import com.datn.vpp.sp26.domain.model.dto.req.ReqCommentDTO
import com.datn.vpp.sp26.domain.model.dto.res.ResCommentDTO

interface CommentRepository {
    suspend fun createComment(req: ReqCommentDTO): ResultWrapper<Any>
    suspend fun getComment(): ResultWrapper<List<ResCommentDTO>>
}