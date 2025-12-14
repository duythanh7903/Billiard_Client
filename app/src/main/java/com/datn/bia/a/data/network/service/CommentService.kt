package com.datn.bia.a.data.network.service

import com.datn.bia.a.data.network.factory.ResultWrapper
import com.datn.bia.a.domain.model.dto.req.ReqCommentDTO
import com.datn.bia.a.domain.model.dto.res.ResCommentDTO
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface CommentService {
    @POST("comment")
    suspend fun createComment(@Body comment: ReqCommentDTO): ResultWrapper<Any>

    @GET("comment")
    suspend fun getComment(): ResultWrapper<List<ResCommentDTO>>
}