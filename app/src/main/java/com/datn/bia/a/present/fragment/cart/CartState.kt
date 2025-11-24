package com.datn.bia.a.present.fragment.cart

import com.datn.bia.a.domain.model.entity.CartEntity

data class CartState(
    val listCart: List<CartEntity> = emptyList()
) {
}