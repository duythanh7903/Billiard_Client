package com.datn.vpp.sp26.present.fragment.cart

import com.datn.vpp.sp26.domain.model.entity.CartEntity
import com.datn.vpp.sp26.domain.model.entity.ProductEntity

data class CartState(
    val listProductEntity: List<ProductEntity> = emptyList(),
    val listCarts: List<CartEntity> = emptyList(),
) {
}