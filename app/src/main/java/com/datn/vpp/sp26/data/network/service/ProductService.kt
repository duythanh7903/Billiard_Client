package com.datn.vpp.sp26.data.network.service

import com.datn.vpp.sp26.data.network.factory.ResultWrapper
import com.datn.vpp.sp26.domain.model.dto.res.ResProductDTO
import retrofit2.http.GET

interface ProductService {
    @GET("products")
    suspend fun fetchAllProducts(): ResultWrapper<ResProductDTO>
}