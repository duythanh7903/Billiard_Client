package com.datn.bia.a.present.activity.auth.su

import com.datn.bia.a.common.UiState
import com.datn.bia.a.domain.model.dto.res.ResSignUpUserDTO

data class SignUpState(
    val uiState: UiState<ResSignUpUserDTO> = UiState.Idle,
    val username: String = "",
    val email: String = "",
    val password: String = "",
    val confirmPassword: String = ""
) {
}