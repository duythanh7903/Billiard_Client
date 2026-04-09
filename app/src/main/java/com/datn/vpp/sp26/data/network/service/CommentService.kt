package com.datn.vpp.sp26.data.network.service

import com.datn.vpp.sp26.data.network.factory.ResultWrapper
import com.datn.vpp.sp26.data.storage.SharedPrefCommon
import com.datn.vpp.sp26.domain.model.dto.req.ReqCommentDTO
import com.datn.vpp.sp26.domain.model.dto.res.ResCommentDTO
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface CommentService {
    @POST("comment")
    suspend fun createComment(
        @Body comment: ReqCommentDTO,
        @Header("Authorization") token: String = "Bearer ${SharedPrefCommon.token}"
    ): ResultWrapper<Any>

    @GET("comment")
    suspend fun getComment(
        @Header("Authorization") token: String = "Bearer ${SharedPrefCommon.token}"
    ): ResultWrapper<List<ResCommentDTO>>
}