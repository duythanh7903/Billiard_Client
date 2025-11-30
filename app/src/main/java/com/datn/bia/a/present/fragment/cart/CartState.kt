package com.datn.bia.a.present.fragment.cart

import com.datn.bia.a.common.UiState
import com.datn.bia.a.domain.model.dto.res.ResProductDTO
import com.datn.bia.a.domain.model.entity.CartEntity

data class CartState(
    val uiStateProduct: UiState<ResProductDTO> = UiState.Idle,
    val listCarts: List<CartEntity> = emptyList(),
) {
}