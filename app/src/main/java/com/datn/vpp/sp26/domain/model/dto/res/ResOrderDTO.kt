package com.datn.vpp.sp26.domain.model.dto.res

data class ResOrderDTO(
    val _id: String? = null,
    val madh: Int? = null,
    val customerName: String? = null,
    val totalPrice: Double? = null,
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
    val priceBeforeDis: Double? = null,
    val priceAfterDis: Double? = null,
    val name: String? = null,
    val _id: String? = null,
    val color: String? = null,
)

data class OrderProductDetail(
    val _id: String? = null,
    val name: String? = null,
    val price: Double? = null,
    val imageUrl: String? = null,
    // [Mới thêm] - Đón danh sách biến thể trả về từ server
    val variants: List<ProductVariant>? = null
)

// [Mới thêm] - Data class ánh xạ lại Object Variant trong Product schema
data class ProductVariant(
    val _id: String? = null,
    val color: String? = null,
    val price: Double? = null,
    val priceWholesale: Double? = null,
    val quantity: Int? = null,
    val status: Boolean? = null
)
