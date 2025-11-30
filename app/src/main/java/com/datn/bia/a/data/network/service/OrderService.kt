package com.datn.bia.a.data.network.service

import com.datn.bia.a.data.network.factory.ResultWrapper
import com.datn.bia.a.domain.model.dto.req.ReqCheckOutDTO
import com.datn.bia.a.domain.model.dto.res.ResCheckOutDTO
import retrofit2.http.Body
import retrofit2.http.POST

interface OrderService {
    @POST("order")
    suspend fun checkOut(
        @Body reqCheckOutDTO: ReqCheckOutDTO
    ): ResultWrapper<ResCheckOutDTO>
}