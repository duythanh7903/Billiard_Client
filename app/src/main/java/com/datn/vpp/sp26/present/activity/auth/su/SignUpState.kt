package com.datn.vpp.sp26.present.activity.auth.su

import com.datn.vpp.sp26.common.UiState
import com.datn.vpp.sp26.domain.model.dto.res.ResSignUpUserDTO

data class SignUpState(
    val uiState: UiState<ResSignUpUserDTO> = UiState.Idle,
    val username: String = "",
    val email: String = "",
    val password: String = "",
    val confirmPassword: String = ""
) {
}