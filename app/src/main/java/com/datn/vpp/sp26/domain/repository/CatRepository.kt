package com.datn.vpp.sp26.domain.repository

import com.datn.vpp.sp26.data.network.factory.ResultWrapper
import com.datn.vpp.sp26.domain.model.dto.res.ResCatDTO

interface CatRepository {
    suspend fun fetchAllCat(): ResultWrapper<List<ResCatDTO>>
}