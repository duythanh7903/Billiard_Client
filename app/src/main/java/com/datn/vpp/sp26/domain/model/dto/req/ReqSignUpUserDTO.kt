package com.datn.vpp.sp26.domain.model.dto.req

data class ReqSignUpUserDTO(
    val username: String,
    val email: String,
    val password: String,
    val phone: String,
    val confirmPassword: String = password,
    val role: String
)