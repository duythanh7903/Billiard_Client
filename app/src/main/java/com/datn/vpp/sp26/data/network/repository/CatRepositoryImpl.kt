package com.datn.vpp.sp26.data.network.repository

import com.datn.vpp.sp26.data.network.factory.ResultWrapper
import com.datn.vpp.sp26.data.network.service.CatService
import com.datn.vpp.sp26.domain.model.dto.res.ResCatDTO
import com.datn.vpp.sp26.domain.repository.CatRepository
import javax.inject.Inject

class CatRepositoryImpl @Inject constructor(
    private val catService: CatService
): CatRepository {
    override suspend fun fetchAllCat(): ResultWrapper<List<ResCatDTO>> =
        catService.fetchAllCat()
}