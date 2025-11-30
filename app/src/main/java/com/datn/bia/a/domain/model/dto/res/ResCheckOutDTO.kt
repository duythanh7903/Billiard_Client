package com.datn.bia.a.domain.model.dto.res

import com.google.gson.annotations.SerializedName

data class ResCheckOutDTO(
    @SerializedName("madh")
    val idOrder: Int? = null,
    val customerName: String? = null,
    val totalPrice: Long? = null,
    val phone: String? = null,
    val address: String? = null,
    val products: List<ProductItem>? = null,
    val status: String? = null,
    val payment: String? = null,
    val userId: String? = null,
    val voucherId: String? = null,
    val note: String? = null,
    @SerializedName("_id")
    val id: String? = null,
    val orderDate: String? = null,
    val createdAt: String? = null,
    val updatedAt: String? = null,
    @SerializedName("__v")
    val v: Int? = null
)

data class ProductItem(
    val productId: String? = null,
    val quantity: Int? = null,
    @SerializedName("_id")
    val id: String? = null
)
