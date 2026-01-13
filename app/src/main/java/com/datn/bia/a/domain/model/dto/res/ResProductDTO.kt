package com.datn.bia.a.domain.model.dto.res

import com.google.gson.annotations.SerializedName

data class ResProductDTO(
    val pagination: ResPagination? = null,
    val data: List<ResProductDataDTO>? = null
) {
}

data class ResProductDataDTO(
    @SerializedName("_id")
    val id: String? = null,
    val name: String? = null,
    val price: Double? = null,
    val variants: List<ResVariantDTO>? = null,
    @SerializedName("caterori")
    val category: ResCatProductDTO? = null,
    val imageUrl: String? = null,
    @SerializedName("abumImage")
    val albumImage: List<String>? = null,
    @SerializedName("description")
    val des: String? = null,
    val status: Boolean? = null,
    val createdAt: String? = null,
    val updatedAt: String? = null,
    val discount: Int? = null
)

data class ResCatProductDTO(
    @SerializedName("_id")
    val id: String? = null,
    val name: String? = null
)

data class ResVariantDTO(
    val color: String? = null,
    val price: Double? = null,
    val quantity: Int? = null,
    val status: Boolean? = null,
    val _id: String? = null
)