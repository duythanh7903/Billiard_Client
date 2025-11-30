package com.datn.bia.a.domain.model.domain

data class Cart(
    val cartId: Long = 0,
    val productId: String = "",
    val productQuantity: Int = 0,
    val productPrice: Int = 0,
    val productDiscount: Int = 0,
    val productImage: String = "",
    val productName: String = ""
) {
}