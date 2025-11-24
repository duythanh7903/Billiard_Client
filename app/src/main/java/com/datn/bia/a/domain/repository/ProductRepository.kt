package com.datn.bia.a.domain.repository

import com.datn.bia.a.data.network.factory.ResultWrapper
import com.datn.bia.a.domain.model.dto.res.ResProductDTO

interface ProductRepository {
    suspend fun fetchAllProducts(): ResultWrapper<ResProductDTO>
}