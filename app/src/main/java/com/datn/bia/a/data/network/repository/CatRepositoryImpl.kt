package com.datn.bia.a.data.network.repository

import com.datn.bia.a.data.network.factory.ResultWrapper
import com.datn.bia.a.data.network.service.CatService
import com.datn.bia.a.domain.model.dto.res.ResCatDTO
import com.datn.bia.a.domain.repository.CatRepository
import javax.inject.Inject

class CatRepositoryImpl @Inject constructor(
    private val catService: CatService
): CatRepository {
    override suspend fun fetchAllCat(): ResultWrapper<List<ResCatDTO>> =
        catService.fetchAllCat()
}