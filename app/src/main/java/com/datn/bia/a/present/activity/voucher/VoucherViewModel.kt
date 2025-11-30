package com.datn.bia.a.present.activity.voucher

import androidx.lifecycle.viewModelScope
import com.datn.bia.a.common.UiState
import com.datn.bia.a.common.base.BaseViewModel
import com.datn.bia.a.domain.usecase.voucher.FetchAllVouchersUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class VoucherViewModel @Inject constructor(
    fetchAllVouchersUseCase: FetchAllVouchersUseCase
) : BaseViewModel() {
    private val _state = MutableStateFlow(VoucherState())
    val state = combine(
        _state,
        fetchAllVouchersUseCase.invoke()
    ) { state, uiState ->
        state.copy(
            uiState = uiState
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(
            stopTimeoutMillis = 5_000
        ), initialValue = VoucherState()
    )

    fun changeStateToIdle() {
        _state.value = _state.value.copy(
            uiState = UiState.Idle
        )
    }
}