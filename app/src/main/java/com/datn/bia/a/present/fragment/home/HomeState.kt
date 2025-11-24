package com.datn.bia.a.present.fragment.home

import com.datn.bia.a.common.UiState
import com.datn.bia.a.domain.model.dto.res.ResCatDTO
import com.datn.bia.a.domain.model.dto.res.ResProductDTO

data class HomeState(
    var productState: UiState<ResProductDTO> = UiState.Idle,
    var catState: UiState<List<ResCatDTO>> = UiState.Idle
) {
}