package com.datn.bia.a.domain.model.dto.res

import com.google.gson.annotations.SerializedName

data class ResVoucherDTO(
    @SerializedName("_id")
    val id: String? = null,
    val code: String? = null,
    val discount: Int? = null,
    val endDate: String? = null,
    val quantity: Int? = null,
    val description: String? = null,
    val startDate: String? = null,
    val isActive: Boolean? = null,
    @SerializedName("__v")
    val v: Int? = null
) {
}