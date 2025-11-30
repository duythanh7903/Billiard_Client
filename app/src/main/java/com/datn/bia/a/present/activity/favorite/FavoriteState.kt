package com.datn.bia.a.present.activity.favorite

import com.datn.bia.a.common.UiState
import com.datn.bia.a.domain.model.dto.res.ResProductDTO
import com.datn.bia.a.domain.model.entity.FavoriteEntity

data class FavoriteState(
    val uiState: UiState<ResProductDTO> = UiState.Idle,
    val listFavorite: List<FavoriteEntity> = emptyList()
) {
}