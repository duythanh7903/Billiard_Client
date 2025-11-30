package com.datn.bia.a.data.network.repository

import com.datn.bia.a.data.network.factory.ResultWrapper
import com.datn.bia.a.data.network.service.VoucherService
import com.datn.bia.a.domain.model.dto.res.ResVoucherDTO
import com.datn.bia.a.domain.repository.VoucherRepository
import javax.inject.Inject

class VoucherRepositoryImpl @Inject constructor(
    private val voucherService: VoucherService
) : VoucherRepository {
    override suspend fun fetchAllVouchers(): ResultWrapper<List<ResVoucherDTO>> =
        voucherService.fetchAllVouchers()
}