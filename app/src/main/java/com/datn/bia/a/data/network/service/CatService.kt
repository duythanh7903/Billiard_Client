package com.datn.bia.a.data.network.service

import com.datn.bia.a.data.network.factory.ResultWrapper
import com.datn.bia.a.domain.model.dto.res.ResCatDTO
import retrofit2.http.GET

interface CatService {
    @GET("categorys")
    suspend fun fetchAllCat(): ResultWrapper<List<ResCatDTO>>
}