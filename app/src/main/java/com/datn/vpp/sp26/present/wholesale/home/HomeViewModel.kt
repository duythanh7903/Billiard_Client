package com.datn.vpp.sp26.present.wholesale.home

import androidx.lifecycle.viewModelScope
import com.datn.vpp.sp26.common.UiState
import com.datn.vpp.sp26.common.base.BaseViewModel
import com.datn.vpp.sp26.domain.model.dto.res.ResCatDTO
import com.datn.vpp.sp26.domain.model.dto.res.ResProductDataDTO
import com.datn.vpp.sp26.domain.usecase.cat.FetchAllCatUseCase
import com.datn.vpp.sp26.domain.usecase.cat_cache.GetListCatCacheUseCase
import com.datn.vpp.sp26.domain.usecase.prod_cache.GetListProductCacheUseCase
import com.datn.vpp.sp26.domain.usecase.product.FetchAllProductsUseCase
import com.datn.vpp.sp26.present.user.fragment.home.HomeState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    fetchAllProductsUseCase: FetchAllProductsUseCase,
    fetchAllCatUseCase: FetchAllCatUseCase,

    getAllProductCacheUseCase: GetListProductCacheUseCase,
    getAllCategoryCacheUseCase: GetListCatCacheUseCase
) : BaseViewModel() {

    val stateProduct = getAllProductCacheUseCase.invoke()
    val stateCate = getAllCategoryCacheUseCase.invoke()

    private val _allCat = MutableStateFlow(emptyList<ResCatDTO>())
    val allCat = _allCat.asStateFlow()
    private val _allPro = MutableStateFlow(emptyList<ResProductDataDTO>())
    val allPro = _allPro.asStateFlow()
    private val _state = MutableStateFlow(HomeState())
    val state = combine(
        _state,
        fetchAllProductsUseCase.invoke(),
        fetchAllCatUseCase.invoke()
    ) { state, productState, catState ->
        state.copy(
            productState = productState,
            catState = catState
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(
            stopTimeoutMillis = 5000
        ), initialValue = HomeState()
    )

    fun changeProductStateToIdle() {
        _state.value = _state.value.copy(
            productState = UiState.Idle
        )
    }

    fun changeCatStateToIdle() {
        _state.value = _state.value.copy(
            catState = UiState.Idle
        )
    }

    fun cacheAllCat(list: List<ResCatDTO>) {
        _allCat.value = list
    }

    fun cacheAllPro(list: List<ResProductDataDTO>) {
        _allPro.value = list
    }
}