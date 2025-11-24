package com.datn.bia.a.domain.model.dto.res

import com.google.gson.annotations.SerializedName

data class ResCatDTO(
    @SerializedName("_id")
    val id: String? = null,
    val name: String? = null,
    val isActive: Boolean? = null,
    @SerializedName("__v")
    val v: Int? = null
) {
}