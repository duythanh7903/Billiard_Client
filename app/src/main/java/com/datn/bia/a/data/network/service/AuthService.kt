package com.datn.bia.a.data.network.service

import com.datn.bia.a.data.network.factory.ResultWrapper
import com.datn.bia.a.domain.model.dto.req.ReqLoginUserDTO
import com.datn.bia.a.domain.model.dto.req.ReqSignUpUserDTO
import com.datn.bia.a.domain.model.dto.req.ReqUpdateAddressDTO
import com.datn.bia.a.domain.model.dto.req.ReqUpdatePhoneDTO
import com.datn.bia.a.domain.model.dto.res.ResLoginUserDTO
import com.datn.bia.a.domain.model.dto.res.ResSignUpUserDTO
import com.datn.bia.a.domain.model.dto.res.ResUpdatePhoneDTO
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path

interface AuthService {
    @POST("login")
    suspend fun loginUser(
        @Body req: ReqLoginUserDTO
    ): ResultWrapper<ResLoginUserDTO>

    @POST("register")
    suspend fun signUpUser(
        @Body req: ReqSignUpUserDTO
    ): ResultWrapper<ResSignUpUserDTO>

    @PATCH("user/{id}")
    suspend fun updatePhoneNumber(
        @Path("id") orderId: String,
        @Body req: ReqUpdatePhoneDTO
    ): ResultWrapper<ResUpdatePhoneDTO>

    @PATCH("user/{id}")
    suspend fun updateAddress(
        @Path("id") orderId: String,
        @Body req: ReqUpdateAddressDTO
    ): ResultWrapper<ResUpdatePhoneDTO>
}