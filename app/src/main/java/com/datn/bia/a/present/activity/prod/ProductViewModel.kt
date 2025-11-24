package com.datn.bia.a.present.activity.prod

import androidx.lifecycle.viewModelScope
import com.datn.bia.a.common.base.BaseViewModel
import com.datn.bia.a.domain.usecase.cart.InsertCartUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductViewModel @Inject constructor(
    private val insertCartUseCase: InsertCartUseCase
): BaseViewModel() {
    fun addProductToCart(productId: String) = viewModelScope.launch {
        insertCartUseCase.invoke(productId).collect {

        }
    }
}