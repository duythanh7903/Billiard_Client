package com.datn.bia.a.present.activity.search

import com.datn.bia.a.common.UiState
import com.datn.bia.a.domain.model.dto.res.ResProductDTO

data class SearchState(
    var productState: UiState<ResProductDTO> = UiState.Idle,
) {
}