package com.datn.bia.a.domain.model.dto.req

import com.google.gson.annotations.SerializedName

data class ReqCheckOutDTO(
    @SerializedName("madh")
    val id: Int = (1 .. 9999999).random(),
    val customerName: String = "",
    val totalPrice: Int = 0,
    val phone: String = "",
    val address: String = "",
    val products: List<ReqProdCheckOut> = emptyList(),
    val status: String = "Xác nhận",
    val payment: String = "COD",
    val userId: String = "",
    val voucherId: String? = null,
    val note: String = ""
) {
}

data class ReqProdCheckOut(
    val productId: String = "",
    val quantity: Int = 0,
    val name: String = "",
    val priceBeforeDis: Int = 0,
    val priceAfterDis: Int = 0
)