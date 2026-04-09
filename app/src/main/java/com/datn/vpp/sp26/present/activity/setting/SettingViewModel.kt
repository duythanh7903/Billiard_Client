package com.datn.vpp.sp26.present.activity.setting

import com.datn.vpp.sp26.common.UiState
import com.datn.vpp.sp26.common.base.BaseViewModel
import com.datn.vpp.sp26.data.storage.SharedPrefCommon
import com.datn.vpp.sp26.domain.model.dto.req.ReqUpdateAddressDTO
import com.datn.vpp.sp26.domain.model.dto.req.ReqUpdatePhoneDTO
import com.datn.vpp.sp26.domain.model.dto.res.ResLoginUserDTO
import com.datn.vpp.sp26.domain.model.dto.res.ResUpdatePhoneDTO
import com.datn.vpp.sp26.domain.usecase.auth.UpdateAddressUseCase
import com.datn.vpp.sp26.domain.usecase.auth.UpdatePhoneNumberUseCase
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class SettingViewModel @Inject constructor(
    private val updatePhoneUseCase: UpdatePhoneNumberUseCase,
    private val updateAddressUseCase: UpdateAddressUseCase
) : BaseViewModel() {
    private val _stateUpdatePhone = MutableStateFlow<UiState<ResUpdatePhoneDTO>>(UiState.Idle)
    val stateUpdatePhone = _stateUpdatePhone.asStateFlow()

    private val _stateUpdateAddress = MutableStateFlow<UiState<ResUpdatePhoneDTO>>(UiState.Idle)
    val stateUpdateAddress = _stateUpdateAddress.asStateFlow()

    fun updatePhone(phone: String) = launchIO {
        val id =
            Gson().fromJson(SharedPrefCommon.jsonAcc, ResLoginUserDTO::class.java)?.user?.id ?: ""
        updatePhoneUseCase.invoke(
            id, ReqUpdatePhoneDTO(phone)
        ).collect { state ->
            _stateUpdatePhone.emit(state)
        }
    }

    fun changeStateToIdle() {
        _stateUpdatePhone.value = UiState.Idle
    }

    fun updateAddress(address: String) = launchIO {
        val id =
            Gson().fromJson(SharedPrefCommon.jsonAcc, ResLoginUserDTO::class.java)?.user?.id ?: ""
        updateAddressUseCase.invoke(
            id, ReqUpdateAddressDTO(address)
        ).collect { state ->
            _stateUpdateAddress.emit(state)
        }
    }

    fun changeStateAddressToIdle() {
        _stateUpdateAddress.value = UiState.Idle
    }
}