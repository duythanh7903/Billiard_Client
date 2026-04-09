package com.datn.vpp.sp26.present.activity.auth.pass

import com.datn.vpp.sp26.common.UiState
import com.datn.vpp.sp26.common.base.BaseViewModel
import com.datn.vpp.sp26.domain.model.dto.req.ReqForgotPass
import com.datn.vpp.sp26.domain.model.dto.res.ResForgotPass
import com.datn.vpp.sp26.domain.usecase.auth.ForgotPassUseCase
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