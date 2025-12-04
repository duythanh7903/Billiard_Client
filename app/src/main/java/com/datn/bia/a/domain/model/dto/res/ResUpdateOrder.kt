package com.datn.bia.a.domain.model.dto.res

data class ResUpdateOrder(
    val __v: Int? = null,
    val _id: String? = null,
    val address: String? = null,
    val createdAt: String? = null,
    val customerName: String? = null,
    val isPaymentSucces: Boolean? = null,
    val madh: Int? = null,
    val note: String? = null,
    val orderDate: String? = null,
    val payment: String? = null,
    val phone: String? = null,
    val products: List<Product?>? = null,
    val status: String? = null,
    val totalPrice: Int? = null,
    val updatedAt: String? = null,
    val userId: String? = null,
    val voucherId: String? = null
)

data class Product(
    val _id: String? = null,
    val name: String? = null,
    val priceAfterDis: Int? = null,
    val priceBeforeDis: Int? = null,
    val productId: String? = null,
    val quantity: Int? = null
)