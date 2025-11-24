package com.datn.bia.a.present.activity.auth.si

import com.datn.bia.a.common.UiState
import com.datn.bia.a.domain.model.dto.res.ResLoginUserDTO

data class SignInState(
    val uiState: UiState<ResLoginUserDTO> = UiState.Idle,
    val email: String = "",
    val password: String = ""
) {
}