package com.datn.bia.a.data.network.repository

import com.datn.bia.a.data.network.factory.ResultWrapper
import com.datn.bia.a.data.network.service.AuthService
import com.datn.bia.a.domain.model.dto.req.ReqLoginUserDTO
import com.datn.bia.a.domain.model.dto.req.ReqSignUpUserDTO
import com.datn.bia.a.domain.model.dto.req.ReqUpdateAddressDTO
import com.datn.bia.a.domain.model.dto.req.ReqUpdatePhoneDTO
import com.datn.bia.a.domain.model.dto.res.ResLoginUserDTO
import com.datn.bia.a.domain.model.dto.res.ResSignUpUserDTO
import com.datn.bia.a.domain.model.dto.res.ResUpdatePhoneDTO
import com.datn.bia.a.domain.repository.AuthRepository
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val authService: AuthService
): AuthRepository {
    override suspend fun loginUser(req: ReqLoginUserDTO): ResultWrapper<ResLoginUserDTO> =
        authService.loginUser(req)

    override suspend fun signUpUser(req: ReqSignUpUserDTO): ResultWrapper<ResSignUpUserDTO> =
        authService.signUpUser(req)

    override suspend fun updatePhoneNumber(
        orderId: String,
        req: ReqUpdatePhoneDTO
    ): ResultWrapper<ResUpdatePhoneDTO> =
        authService.updatePhoneNumber(orderId, req)

    override suspend fun updateAddress(
        id: String,
        req: ReqUpdateAddressDTO
    ): ResultWrapper<ResUpdatePhoneDTO> =
        authService.updateAddress(id, req)
}