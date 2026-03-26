package com.datn.bia.a.data.network.service

import com.datn.bia.a.data.network.factory.ResultWrapper
import com.datn.bia.a.data.storage.SharedPrefCommon
import com.datn.bia.a.domain.model.dto.res.ResVoucherDTO
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers

interface VoucherService {
    @GET("vouchers")
    suspend fun fetchAllVouchers(
        @Header("Authorization") token: String = "Bearer ${SharedPrefCommon.token}"
    ): ResultWrapper<List<ResVoucherDTO>>
}