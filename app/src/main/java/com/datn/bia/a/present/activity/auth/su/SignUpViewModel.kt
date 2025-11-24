package com.datn.bia.a.present.activity.auth.su

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.datn.bia.a.common.UiState
import com.datn.bia.a.common.base.BaseViewModel
import com.datn.bia.a.domain.model.dto.req.ReqLoginUserDTO
import com.datn.bia.a.domain.model.dto.req.ReqSignUpUserDTO
import com.datn.bia.a.domain.usecase.auth.SignUpUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val signUpUserUseCase: SignUpUserUseCase
) : BaseViewModel() {
    private val _usernameValue = MutableStateFlow("")
    private val _emailValue = MutableStateFlow("")
    private val _passwordValue = MutableStateFlow("")
    private val _confirmPasswordValue = MutableStateFlow("")
    private val _state = MutableStateFlow(SignUpState())
    val state = combine(
        _state,
        _usernameValue,
        _emailValue,
        _passwordValue,
        _confirmPasswordValue
    ) { state, username, email, password, confirmPass ->
        state.copy(
            username = username,
            email = email,
            password = password,
            confirmPassword = confirmPass
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(
            stopTimeoutMillis = 5000
        ), initialValue = SignUpState()
    )

    fun changeEmailValue(value: String) {
        _emailValue.value = value
    }

    fun changePasswordValue(value: String) {
        _passwordValue.value = value
    }

    fun changeUsernameValue(value: String) {
        _usernameValue.value = value
    }

    fun changeConfirmPasswordValue(value: String) {
        _confirmPasswordValue.value = value
    }

    fun changeStateToIdle() {
        _state.value = _state.value.copy(
            uiState = UiState.Idle
        )
    }

    fun onSignUpEvent() {
        val req = ReqSignUpUserDTO(
            username = _state.value.username,
            email = _state.value.email,
            password = _state.value.password,
        )
        loginUser(req)
    }

    private fun loginUser(req: ReqSignUpUserDTO) = viewModelScope.launch {
        signUpUserUseCase.invoke(req).collect { uiState ->
            _state.value = _state.value.copy(
                uiState = uiState
            )
        }
    }
}