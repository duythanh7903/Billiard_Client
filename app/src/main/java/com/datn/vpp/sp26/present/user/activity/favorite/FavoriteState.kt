package com.datn.vpp.sp26.present.user.activity.favorite

import com.datn.vpp.sp26.common.UiState
import com.datn.vpp.sp26.domain.model.dto.res.ResProductDTO
import com.datn.vpp.sp26.domain.model.entity.FavoriteEntity

data class FavoriteState(
    val uiState: UiState<ResProductDTO> = UiState.Idle,
    val listFavorite: List<FavoriteEntity> = emptyList()
) {
}