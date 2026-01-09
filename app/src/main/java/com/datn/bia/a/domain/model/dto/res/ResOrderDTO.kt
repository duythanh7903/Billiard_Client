package com.datn.bia.a.domain.model.dto.res

data class ResOrderDTO(
    val _id: String? = null,
    val madh: Int? = null,
    val customerName: String? = null,
    val totalPrice: Int? = null,
    val phone: String? = null,
    val address: String? = null,
    val products: List<OrderProduct>? = null,
    val status: String? = null,
    val payment: String? = null,
    val userId: String? = null,
    val voucherId: String? = null,
    val note: String? = null,
    val orderDate: String? = null,
    val createdAt: String? = null,
    val updatedAt: String? = null,
    val __v: Int? = null
)

data class OrderProduct(
    val productId: OrderProductDetail? = null,
    val quantity: Int? = null,
    val priceBeforeDis: Int? = null,
    val priceAfterDis: Int? = null,
    val name: String? = null,
    val _id: String? = null
)

data class OrderProductDetail(
    val _id: String? = null,
    val name: String? = null,
    val price: Int? = null,
    val imageUrl: String? = null
)