package com.datn.bia.a.present.activity.auth.pass

import com.datn.bia.a.common.UiState
import com.datn.bia.a.domain.model.dto.res.ResForgotPass

data class ForgotState(
    val uiState: UiState<ResForgotPass> = UiState.Idle
) {
}