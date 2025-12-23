package com.datn.bia.a.domain.repository

import com.datn.bia.a.data.network.factory.ResultWrapper
import com.datn.bia.a.domain.model.dto.req.ReqForgotPass
import com.datn.bia.a.domain.model.dto.req.ReqLoginUserDTO
import com.datn.bia.a.domain.model.dto.req.ReqResetPass
import com.datn.bia.a.domain.model.dto.req.ReqSignUpUserDTO
import com.datn.bia.a.domain.model.dto.req.ReqUpdateAddressDTO
import com.datn.bia.a.domain.model.dto.req.ReqUpdatePhoneDTO
import com.datn.bia.a.domain.model.dto.res.ResForgotPass
import com.datn.bia.a.domain.model.dto.res.ResLoginUserDTO
import com.datn.bia.a.domain.model.dto.res.ResResetPass
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

    suspend fun forgotPassword(
        req: ReqForgotPass
    ): ResultWrapper<ResForgotPass>

    suspend fun resetPassword(
        req: ReqResetPass,
        token: String
    ): ResultWrapper<ResResetPass>
}