package com.datn.bia.a.data.network.service

import com.datn.bia.a.data.network.factory.ResultWrapper
import com.datn.bia.a.domain.model.dto.res.ResProductDTO
import retrofit2.http.GET

interface ProductService {
    @GET("products")
    suspend fun fetchAllProducts(): ResultWrapper<ResProductDTO>
}