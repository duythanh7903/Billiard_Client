package com.datn.bia.a.domain.model.domain

import com.datn.bia.a.domain.model.dto.res.ResVariantDTO

data class Cart(
    val cartId: Long = 0,
    val productId: String = "",
    val productQuantity: Int = 0,
    val productPrice: Double = 0.0,
    val productDiscount: Int = 0,
    val productImage: String = "",
    val productName: String = "",

    val variant: ResVariantDTO
) {
}