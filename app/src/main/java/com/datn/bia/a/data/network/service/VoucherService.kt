package com.datn.bia.a.data.network.service

import com.datn.bia.a.data.network.factory.ResultWrapper
import com.datn.bia.a.domain.model.dto.res.ResVoucherDTO
import retrofit2.http.GET

interface VoucherService {
    @GET("vouchers")
    suspend fun fetchAllVouchers(): ResultWrapper<List<ResVoucherDTO>>
}