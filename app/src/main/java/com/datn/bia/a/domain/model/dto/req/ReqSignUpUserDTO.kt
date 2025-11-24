package com.datn.bia.a.domain.model.dto.req

data class ReqSignUpUserDTO(
    val username: String,
    val email: String,
    val password: String,
    val role: String = "user", // follow by server
) {
}