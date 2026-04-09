package com.datn.vpp.sp26.data.network.repository

import com.datn.vpp.sp26.data.network.factory.ResultWrapper
import com.datn.vpp.sp26.data.network.service.VoucherService
import com.datn.vpp.sp26.domain.model.dto.res.ResVoucherDTO
import com.datn.vpp.sp26.domain.repository.VoucherRepository
import javax.inject.Inject

class VoucherRepositoryImpl @Inject constructor(
    private val voucherService: VoucherService
) : VoucherRepository {
    override suspend fun fetchAllVouchers(): ResultWrapper<List<ResVoucherDTO>> =
        voucherService.fetchAllVouchers()
}