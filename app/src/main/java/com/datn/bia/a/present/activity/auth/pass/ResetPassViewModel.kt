package com.datn.bia.a.present.activity.auth.pass

import com.datn.bia.a.common.UiState
import com.datn.bia.a.common.base.BaseViewModel
import com.datn.bia.a.domain.model.dto.req.ReqResetPass
import com.datn.bia.a.domain.model.dto.res.ResResetPass
import com.datn.bia.a.domain.usecase.auth.ResetPassUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class ResetPassViewModel @Inject constructor(
    private val resetPassUseCase: ResetPassUseCase
): BaseViewModel() {
    private val _uiState = MutableStateFlow<UiState<ResResetPass>>(UiState.Idle)
    val uiState = _uiState.asStateFlow()

    fun handleResetPass(req: ReqResetPass, token: String) = launchIO {
        resetPassUseCase.invoke(req, token).collect { uiState ->
            _uiState.value = uiState
        }
    }
}