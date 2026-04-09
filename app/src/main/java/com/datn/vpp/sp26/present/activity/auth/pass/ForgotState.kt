package com.datn.vpp.sp26.present.activity.auth.pass

import com.datn.vpp.sp26.common.UiState
import com.datn.vpp.sp26.domain.model.dto.res.ResForgotPass

data class ForgotState(
    val uiState: UiState<ResForgotPass> = UiState.Idle
) {
}