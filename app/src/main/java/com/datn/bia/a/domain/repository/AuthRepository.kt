package com.datn.bia.a.domain.repository

import com.datn.bia.a.data.network.factory.ResultWrapper
import com.datn.bia.a.domain.model.dto.req.ReqLoginUserDTO
import com.datn.bia.a.domain.model.dto.req.ReqSignUpUserDTO
import com.datn.bia.a.domain.model.dto.res.ResLoginUserDTO
import com.datn.bia.a.domain.model.dto.res.ResSignUpUserDTO

interface AuthRepository {
    suspend fun loginUser(
        req: ReqLoginUserDTO
    ): ResultWrapper<ResLoginUserDTO>

    suspend fun signUpUser(
        req: ReqSignUpUserDTO
    ): ResultWrapper<ResSignUpUserDTO>
}