package com.datn.bia.a.present.activity.auth.pass

import com.datn.bia.a.common.UiState
import com.datn.bia.a.common.base.BaseViewModel
import com.datn.bia.a.domain.model.dto.req.ReqForgotPass
import com.datn.bia.a.domain.model.dto.res.ResForgotPass
import com.datn.bia.a.domain.usecase.auth.ForgotPassUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class ForgotViewModel @Inject constructor(
    private val forgotPassUseCase: ForgotPassUseCase
): BaseViewModel() {

    private val _uiState = MutableStateFlow<UiState<ResForgotPass>>(UiState.Idle)
    val uiState = _uiState.asStateFlow()

    fun forgotPass(req: ReqForgotPass) = launchIO {
        forgotPassUseCase.invoke(req).collect { state ->
            _uiState.value = state
        }
    }

}