package com.datn.vpp.sp26.present.wholesale.profile

import androidx.lifecycle.viewModelScope
import com.datn.vpp.sp26.common.UiState
import com.datn.vpp.sp26.common.base.BaseViewModel
import com.datn.vpp.sp26.data.storage.SharedPrefCommon
import com.datn.vpp.sp26.domain.model.dto.req.ReqUpdatePhoneAndAddress
import com.datn.vpp.sp26.domain.model.dto.res.ResLoginUserDTO
import com.datn.vpp.sp26.domain.model.dto.res.ResUpdatePhoneAndAddressDTO
import com.datn.vpp.sp26.domain.usecase.auth.UpdatePhoneNumberAndAddressUseCase
import com.datn.vpp.sp26.domain.usecase.cart.GetAllCartsUseCase
import com.datn.vpp.sp26.domain.usecase.order.GetAllOrderByIdUseCase
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    getAllCartUseCase: GetAllCartsUseCase,
    getAllOrderByIdUseCase: GetAllOrderByIdUseCase,

    private val updatePhoneNumberAndAddressUseCase: UpdatePhoneNumberAndAddressUseCase,

    ) : BaseViewModel() {
    private val _state = MutableStateFlow(ProfileState())
    val state = combine(
        _state,
        getAllCartUseCase.invoke(SharedPrefCommon.idUser),
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

    private val _stateOrder = MutableStateFlow<UiState<ResUpdatePhoneAndAddressDTO>>(UiState.Idle)
    val stateOrder = _stateOrder.asStateFlow()

    fun updatePhoneNumberAndAddress(
        id: String,
        phone: String,
        address: String
    ) = launchIO {
        updatePhoneNumberAndAddressUseCase.invoke(
            id,
            ReqUpdatePhoneAndAddress(
                phone = phone,
                address = address
            )
        ).collect {
            _stateOrder.emit(it)
        }
    }
}