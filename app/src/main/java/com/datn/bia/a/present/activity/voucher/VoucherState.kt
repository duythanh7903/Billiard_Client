package com.datn.bia.a.present.activity.voucher

import com.datn.bia.a.common.UiState
import com.datn.bia.a.domain.model.dto.res.ResVoucherDTO

data class VoucherState(
    val uiState: UiState<List<ResVoucherDTO>> = UiState.Idle
) {
}