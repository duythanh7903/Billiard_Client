package com.datn.bia.a.present.fragment.cart

import com.datn.bia.a.domain.model.entity.CartEntity
import com.datn.bia.a.domain.model.entity.ProductEntity

data class CartState(
    val listProductEntity: List<ProductEntity> = emptyList(),
    val listCarts: List<CartEntity> = emptyList(),
) {
}