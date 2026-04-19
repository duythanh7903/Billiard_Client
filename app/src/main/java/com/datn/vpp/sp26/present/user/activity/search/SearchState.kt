package com.datn.vpp.sp26.present.user.activity.search

import com.datn.vpp.sp26.common.UiState
import com.datn.vpp.sp26.domain.model.dto.res.ResProductDTO

data class SearchState(
    var productState: UiState<ResProductDTO> = UiState.Idle,
) {
}