package com.datn.bia.a.present.fragment.cart

import android.content.Context
import androidx.lifecycle.viewModelScope
import com.datn.bia.a.common.UiState
import com.datn.bia.a.common.base.BaseViewModel
import com.datn.bia.a.domain.model.dto.res.ResCheckOutDTO
import com.datn.bia.a.domain.model.dto.res.ResVoucherDTO
import com.datn.bia.a.domain.usecase.cart.GetAllCartsUseCase
import com.datn.bia.a.domain.usecase.cart.IncreaseCartUseCase
import com.datn.bia.a.domain.usecase.cart.ReduceCartUseCase
import com.datn.bia.a.domain.usecase.prod_cache.GetListProductCacheUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    getListProductCacheUseCase: GetListProductCacheUseCase,
    getAllCartsUseCase: GetAllCartsUseCase,

    private val increaseCartUseCase: IncreaseCartUseCase,
    private val reduceCartUseCase: ReduceCartUseCase,
) : BaseViewModel() {
    private val _stateCheckOut = MutableStateFlow<UiState<ResCheckOutDTO>>(UiState.Idle)
    val stateCheckOut = _stateCheckOut.asStateFlow()
    private val _voucherSelected = MutableStateFlow<ResVoucherDTO?>(null)
    val voucherSelected = _voucherSelected.asStateFlow()
    private val _listIdCartSelected = MutableStateFlow(mutableListOf<Long>())
    val listIdCartSelected = _listIdCartSelected.asStateFlow()
    private val _flagIncreaseCart = MutableStateFlow<Boolean?>(null)
    val flagIncreaseCart = _flagIncreaseCart.asStateFlow()
    private val _flagReduceCart = MutableStateFlow<Boolean?>(null)
    val flagReduceCart = _flagReduceCart.asStateFlow()

    private val _state = MutableStateFlow(CartState())
    val state = combine(
        _state,
        getListProductCacheUseCase.invoke(),
        getAllCartsUseCase.invoke()
    ) { state, listproductEntity, listCarts ->
        state.copy(
            listProductEntity = listproductEntity,
            listCarts = listCarts
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(
            stopTimeoutMillis = 5000
        ), initialValue = CartState()
    )

    fun inCreaseCart(idCart: Long) = viewModelScope.launch {
        increaseCartUseCase.invoke(idCart).collect { isSuccess ->
            _flagIncreaseCart.emit(isSuccess)
        }
    }

    fun resetFlagIncreaseCart() {
        _flagIncreaseCart.value = null
    }

    fun reduceCart(idCart: Long) = viewModelScope.launch {
        reduceCartUseCase.invoke(idCart).collect { isSuccess ->
            _flagReduceCart.emit(isSuccess)
        }
    }

    fun resetFlagReduceCart() {
        _flagReduceCart.value = null
    }

    fun selectCart(id: Long) {
        val listId = _listIdCartSelected.value.toMutableList()
        if (listId.contains(id)) listId.remove(id) else listId.add(id)
        _listIdCartSelected.value = listId
    }

    fun selectAllCart(listId: List<Long>) {
        _listIdCartSelected.value = listId.toMutableList()
    }

    fun unSelectAllCart() {
        _listIdCartSelected.value = mutableListOf()
    }

    fun changeVoucher(voucher: ResVoucherDTO) {
        _voucherSelected.value = voucher
    }

    fun resetStateCheckOutToIdle() {
        _stateCheckOut.value = UiState.Idle
    }
}