package com.datn.bia.a.present.activity.search

import androidx.lifecycle.viewModelScope
import com.datn.bia.a.common.UiState
import com.datn.bia.a.common.base.BaseViewModel
import com.datn.bia.a.domain.model.dto.res.ResProductDataDTO
import com.datn.bia.a.domain.usecase.product.FetchAllProductsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    fetchAllProductsUseCase: FetchAllProductsUseCase,
) : BaseViewModel() {
    private val _state = MutableStateFlow(SearchState())
    val state = combine(
        _state,
        fetchAllProductsUseCase.invoke(),
    ) { state, productState ->
        state.copy(
            productState = productState,
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(
            stopTimeoutMillis = 5000
        ), initialValue = SearchState()
    )

    fun changeStateProductToIdle() {
        _state.value = _state.value.copy(
            productState = UiState.Idle
        )
    }

    private val _listProduct = MutableStateFlow<List<ResProductDataDTO>>(emptyList())
    val listProduct = _listProduct.asStateFlow()

    fun cacheListProduct(list: List<ResProductDataDTO>) {
        _listProduct.value = list
    }

    private val _keySearch = MutableStateFlow("")
    val keySearch = _keySearch.asStateFlow()

    fun changeKeySearch(key: String?) {
        _keySearch.value = key ?: ""
    }
}