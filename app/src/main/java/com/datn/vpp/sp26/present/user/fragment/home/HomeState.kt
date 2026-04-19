package com.datn.vpp.sp26.present.user.fragment.home

import com.datn.vpp.sp26.common.UiState
import com.datn.vpp.sp26.domain.model.dto.res.ResCatDTO
import com.datn.vpp.sp26.domain.model.dto.res.ResProductDTO

data class HomeState(
    var productState: UiState<ResProductDTO> = UiState.Idle,
    var catState: UiState<List<ResCatDTO>> = UiState.Idle
) {
}