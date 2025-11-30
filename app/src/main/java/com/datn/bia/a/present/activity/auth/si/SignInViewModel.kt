package com.datn.bia.a.present.activity.auth.si

import androidx.lifecycle.viewModelScope
import com.datn.bia.a.common.UiState
import com.datn.bia.a.common.base.BaseViewModel
import com.datn.bia.a.domain.model.dto.req.ReqLoginUserDTO
import com.datn.bia.a.domain.usecase.auth.LoginUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val loginUserUseCase: LoginUserUseCase
) : BaseViewModel() {
    private val _passwordInput = MutableStateFlow("")
    private val _emailInput = MutableStateFlow("")
    private val _state = MutableStateFlow(SignInState())
    val state = combine(
        _state, _emailInput, _passwordInput,
    ) { state, email, password ->
        state.copy(
            email = email,
            password = password
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(
            stopTimeoutMillis = 5000
        ), initialValue = SignInState()
    )

    fun changeEmailValue(value: String) {
        _emailInput.value = value
    }

    fun changePasswordValue(value: String) {
        _passwordInput.value = value
    }

    fun onSignInEvent() {
        val email = _emailInput.value
        val password = _passwordInput.value

        val reqLogin = ReqLoginUserDTO(
            email = email,
            password = password
        )
        loginUser(reqLogin)
    }

    fun changeStateToIdle() {
        _state.value = _state.value.copy(
            uiState = UiState.Idle
        )
    }

    private fun loginUser(req: ReqLoginUserDTO) = viewModelScope.launch {
        loginUserUseCase.invoke(req).collect { uiState ->
            _state.value = _state.value.copy(
                uiState = uiState
            )
        }
    }
}