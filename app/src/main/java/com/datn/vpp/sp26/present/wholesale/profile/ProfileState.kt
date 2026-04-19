package com.datn.vpp.sp26.present.wholesale.profile

import com.datn.vpp.sp26.common.UiState
import com.datn.vpp.sp26.domain.model.dto.res.ResOrderDTO
import com.datn.vpp.sp26.domain.model.entity.CartEntity

data class ProfileState(
    val listCart: List<CartEntity> = emptyList(),
    val uiState: UiState<List<ResOrderDTO>> = UiState.Idle
) {
}