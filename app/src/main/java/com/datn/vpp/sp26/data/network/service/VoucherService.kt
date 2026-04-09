package com.datn.vpp.sp26.data.network.service

import com.datn.vpp.sp26.data.network.factory.ResultWrapper
import com.datn.vpp.sp26.data.storage.SharedPrefCommon
import com.datn.vpp.sp26.domain.model.dto.res.ResVoucherDTO
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers

interface VoucherService {
    @GET("vouchers")
    suspend fun fetchAllVouchers(
        @Header("Authorization") token: String = "Bearer ${SharedPrefCommon.token}"
    ): ResultWrapper<List<ResVoucherDTO>>
}