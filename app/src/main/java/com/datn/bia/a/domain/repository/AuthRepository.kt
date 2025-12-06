package com.datn.bia.a.domain.repository

import com.datn.bia.a.data.network.factory.ResultWrapper
import com.datn.bia.a.domain.model.dto.req.ReqLoginUserDTO
import com.datn.bia.a.domain.model.dto.req.ReqSignUpUserDTO
import com.datn.bia.a.domain.model.dto.req.ReqUpdateAddressDTO
import com.datn.bia.a.domain.model.dto.req.ReqUpdatePhoneDTO
import com.datn.bia.a.domain.model.dto.res.ResLoginUserDTO
import com.datn.bia.a.domain.model.dto.res.ResSignUpUserDTO
import com.datn.bia.a.domain.model.dto.res.ResUpdatePhoneDTO

interface AuthRepository {
    suspend fun loginUser(
        req: ReqLoginUserDTO
    ): ResultWrapper<ResLoginUserDTO>

    suspend fun signUpUser(
        req: ReqSignUpUserDTO
    ): ResultWrapper<ResSignUpUserDTO>

    suspend fun updatePhoneNumber(
        orderId: String,
        req: ReqUpdatePhoneDTO
    ): ResultWrapper<ResUpdatePhoneDTO>

    suspend fun updateAddress(
        id: String,
        req: ReqUpdateAddressDTO
    ): ResultWrapper<ResUpdatePhoneDTO>
}