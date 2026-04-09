package com.datn.vpp.sp26.present.activity.order.confirm

import android.content.Context
import android.util.Log
import androidx.lifecycle.viewModelScope
import com.datn.vpp.sp26.R
import com.datn.vpp.sp26.common.AppConst
import com.datn.vpp.sp26.common.UiState
import com.datn.vpp.sp26.common.base.BaseViewModel
import com.datn.vpp.sp26.data.storage.SharedPrefCommon
import com.datn.vpp.sp26.domain.model.dto.req.ReqCheckOutDTO
import com.datn.vpp.sp26.domain.model.dto.req.ReqProdCheckOut
import com.datn.vpp.sp26.domain.model.dto.res.ResCheckOutDTO
import com.datn.vpp.sp26.domain.model.dto.res.ResLoginUserDTO
import com.datn.vpp.sp26.domain.usecase.cart.DeleteCartByIdsUseCase
import com.datn.vpp.sp26.domain.usecase.order.CheckOutOrderUseCase
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ConfirmViewModel @Inject constructor(
    @ApplicationContext
    private val context: Context,
    private val checkOutOrderUseCase: CheckOutOrderUseCase,
    private val deleteCartByIdsUseCase: DeleteCartByIdsUseCase
) : BaseViewModel() {
    private val _message = MutableStateFlow("")
    val message = _message.asStateFlow()

    private val _stateCheckOut = MutableStateFlow<UiState<ResCheckOutDTO>>(UiState.Idle)
    val stateCheckOut = _stateCheckOut.asStateFlow()

    fun setMessage(str: String) {
        _message.value = str
    }

    fun checkOutOrder(
        totalPrice: Double,
        voucherId: String?,
        listProduct: List<ReqProdCheckOut>,
        paymentMethod: String
    ) = viewModelScope.launch {
        Gson().fromJson(SharedPrefCommon.jsonAcc, ResLoginUserDTO::class.java)?.let {
            val req = ReqCheckOutDTO(
                customerName = it.user?.username ?: "",
                totalPrice = totalPrice + AppConst.FEE_SHIP,
                phone = it.user?.phone ?: "0123456789",
                address = it.user?.address
                    ?: "2PQW+6JJ Tòa nhà FPT Polytechnic., Cổng số 2, 13 Trịnh Văn Bô, Xuân Phương, Nam Từ Liêm, Hà Nội 100000, Việt Nam",
                products = listProduct,
                payment = paymentMethod,
                userId = it.user?.id ?: "",
                voucherId = voucherId,
                note = _message.value
            )

            Log.d("json", "${req}")

            checkOutOrderUseCase.invoke(req).collect { uiState ->
                _stateCheckOut.emit(uiState)
            }
        } ?: run {
            _stateCheckOut.emit(UiState.Error(context.getString(R.string.msg_wrong)))
        }
    }

    fun changeStateToIdle() {
        _stateCheckOut.value = UiState.Idle
    }

    fun deleteCartById(ids: List<Long>) = launchIO {
        deleteCartByIdsUseCase.invoke(ids)
    }
}