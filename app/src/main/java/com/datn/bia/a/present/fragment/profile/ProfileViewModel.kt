package com.datn.bia.a.present.fragment.profile

import androidx.lifecycle.viewModelScope
import com.datn.bia.a.common.UiState
import com.datn.bia.a.common.base.BaseViewModel
import com.datn.bia.a.data.storage.SharedPrefCommon
import com.datn.bia.a.domain.model.dto.res.ResLoginUserDTO
import com.datn.bia.a.domain.usecase.cart.GetAllCartsUseCase
import com.datn.bia.a.domain.usecase.order.GetAllOrderByIdUseCase
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    getAllCartUseCase: GetAllCartsUseCase,
    getAllOrderByIdUseCase: GetAllOrderByIdUseCase,
) : BaseViewModel() {
    private val _state = MutableStateFlow(ProfileState())
    val state = combine(
        _state,
        getAllCartUseCase.invoke(),
        getAllOrderByIdUseCase.invoke(
            Gson().fromJson(SharedPrefCommon.jsonAcc, ResLoginUserDTO::class.java)?.user?.id ?: ""
        )
    ) { state, listCartEntity, listResOrder ->
        state.copy(
            listCartEntity,
            listResOrder
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(
            stopTimeoutMillis = 5000
        ), initialValue = ProfileState()
    )

    fun changeStateToIdle() {
        _state.value = _state.value.copy(
            uiState = UiState.Idle
        )
    }
}