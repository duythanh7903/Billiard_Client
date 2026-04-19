package com.datn.vpp.sp26.present.user.activity.voucher

import com.datn.vpp.sp26.common.UiState
import com.datn.vpp.sp26.domain.model.dto.res.ResVoucherDTO

data class VoucherState(
    val uiState: UiState<List<ResVoucherDTO>> = UiState.Idle
) {
}