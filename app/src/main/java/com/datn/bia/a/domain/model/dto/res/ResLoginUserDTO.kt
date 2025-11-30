package com.datn.bia.a.domain.model.dto.res

import com.google.gson.annotations.SerializedName

data class ResLoginUserDTO(
    val user: DataUserLogin? = null,
    val token: String? = null,
    val message: String? = null,
) {
}

data class DataUserLogin(
    @SerializedName("_id")
    val id: String? = null,
    val username: String? = null,
    val email: String? = null,
    val password: String? = null,
    val role: String? = null,
    val active: String? = null,
    val createdAt: String? = null,
    val updatedAt: String? = null,
)