package com.datn.bia.a.domain.repository

import com.datn.bia.a.data.network.factory.ResultWrapper
import com.datn.bia.a.domain.model.dto.req.ReqCheckOutDTO
import com.datn.bia.a.domain.model.dto.res.ResCheckOutDTO

interface OrderRepository {
    suspend fun checkOut(
        reqCheckOutDTO: ReqCheckOutDTO
    ): ResultWrapper<ResCheckOutDTO>
}