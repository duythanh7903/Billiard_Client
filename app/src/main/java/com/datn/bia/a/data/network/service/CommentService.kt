package com.datn.bia.a.data.network.service

import com.datn.bia.a.data.network.factory.ResultWrapper
import com.datn.bia.a.domain.model.dto.req.ReqCommentDTO
import retrofit2.http.Body
import retrofit2.http.POST

interface CommentService {
    @POST("comment")
    suspend fun createComment(@Body comment: ReqCommentDTO): ResultWrapper<Any>
}