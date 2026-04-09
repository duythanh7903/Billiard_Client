package com.datn.vpp.sp26.present.activity.auth.si

import com.datn.vpp.sp26.common.UiState
import com.datn.vpp.sp26.domain.model.dto.res.ResLoginUserDTO

data class SignInState(
    val uiState: UiState<ResLoginUserDTO> = UiState.Idle,
    val email: String = "",
    val password: String = ""
) {
}