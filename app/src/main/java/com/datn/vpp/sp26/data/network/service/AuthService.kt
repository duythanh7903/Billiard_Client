package com.datn.vpp.sp26.data.network.service

import com.datn.vpp.sp26.data.network.factory.ResultWrapper
import com.datn.vpp.sp26.data.storage.SharedPrefCommon
import com.datn.vpp.sp26.domain.model.dto.req.ReqForgotPass
import com.datn.vpp.sp26.domain.model.dto.req.ReqLoginUserDTO
import com.datn.vpp.sp26.domain.model.dto.req.ReqResetPass
import com.datn.vpp.sp26.domain.model.dto.req.ReqSignUpUserDTO
import com.datn.vpp.sp26.domain.model.dto.req.ReqUpdateAddressDTO
import com.datn.vpp.sp26.domain.model.dto.req.ReqUpdatePhoneAndAddress
import com.datn.vpp.sp26.domain.model.dto.req.ReqUpdatePhoneDTO
import com.datn.vpp.sp26.domain.model.dto.res.ResForgotPass
import com.datn.vpp.sp26.domain.model.dto.res.ResLoginUserDTO
import com.datn.vpp.sp26.domain.model.dto.res.ResResetPass
import com.datn.vpp.sp26.domain.model.dto.res.ResSignUpUserDTO
import com.datn.vpp.sp26.domain.model.dto.res.ResUpdatePhoneAndAddressDTO
import com.datn.vpp.sp26.domain.model.dto.res.ResUpdatePhoneDTO
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path

interface AuthService {
    @POST("login")
    suspend fun loginUser(
        @Body req: ReqLoginUserDTO,
    ): ResultWrapper<ResLoginUserDTO>

    @POST("register")
    suspend fun signUpUser(
        @Body req: ReqSignUpUserDTO
    ): ResultWrapper<ResSignUpUserDTO>

    @PATCH("user/{id}")
    suspend fun updatePhoneNumber(
        @Path("id") orderId: String,
        @Body req: ReqUpdatePhoneDTO,
        @Header("Authorization") token: String = "Bearer ${SharedPrefCommon.token}"
    ): ResultWrapper<ResUpdatePhoneDTO>

    @PATCH("user/{id}")
    suspend fun updateAddress(
        @Path("id") orderId: String,
        @Body req: ReqUpdateAddressDTO,
        @Header("Authorization") token: String = "Bearer ${SharedPrefCommon.token}"
    ): ResultWrapper<ResUpdatePhoneDTO>

    @POST("forgot-password")
    suspend fun forgotPassword(
        @Body req: ReqForgotPass,
        @Header("Authorization") token: String = "Bearer ${SharedPrefCommon.token}"
    ): ResultWrapper<ResForgotPass>

    @POST("reset-password/{token}")
    suspend fun resetPassword(
        @Body req: ReqResetPass,
        @Path("token") token: String
    ): ResultWrapper<ResResetPass>

    @PATCH("user/profile/{id}")
    suspend fun updatePhoneNumberAndAddress(
        @Path("id") id: String,
        @Body req: ReqUpdatePhoneAndAddress,
        @Header("Authorization") token: String = "Bearer ${SharedPrefCommon.token}"
    ): ResultWrapper<ResUpdatePhoneAndAddressDTO>
}