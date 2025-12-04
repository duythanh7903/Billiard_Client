package com.datn.bia.a.present.fragment.profile

import com.datn.bia.a.common.UiState
import com.datn.bia.a.domain.model.dto.res.ResOrderDTO
import com.datn.bia.a.domain.model.entity.CartEntity

data class ProfileState(
    val listCart: List<CartEntity> = emptyList(),
    val uiState: UiState<List<ResOrderDTO>> = UiState.Idle
) {
}