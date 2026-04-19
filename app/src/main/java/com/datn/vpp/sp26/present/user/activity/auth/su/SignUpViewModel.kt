package com.datn.vpp.sp26.present.user.activity.auth.su

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.datn.vpp.sp26.common.AppConst
import com.datn.vpp.sp26.common.UiState
import com.datn.vpp.sp26.common.base.BaseViewModel
import com.datn.vpp.sp26.domain.model.dto.req.ReqSignUpUserDTO
import com.datn.vpp.sp26.domain.usecase.auth.SignUpUserUseCase
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
    private val _phoneNumberValue = MutableStateFlow("")
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

    fun changePhoneNumberValue(value: String) {
        _phoneNumberValue.value = value
    }

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

    fun onSignUpEvent(tag: Int) {
        val req = ReqSignUpUserDTO(
            username = _usernameValue.value.ifEmpty { _emailValue.value.split("@")[0] },
            email = _emailValue.value,
            password = _passwordValue.value,
            role = if (tag == 1) AppConst.ROLE_USER else AppConst.ROLE_WHOLESALE,
            phone = _phoneNumberValue.value,
        )
        Log.d("debug", "$req")
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