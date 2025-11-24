package com.datn.bia.a.domain.repository

import com.datn.bia.a.data.network.factory.ResultWrapper
import com.datn.bia.a.domain.model.dto.res.ResCatDTO

interface CatRepository {
    suspend fun fetchAllCat(): ResultWrapper<List<ResCatDTO>>
}