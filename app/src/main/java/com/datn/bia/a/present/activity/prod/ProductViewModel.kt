package com.datn.bia.a.present.activity.prod

import androidx.lifecycle.viewModelScope
import com.datn.bia.a.common.base.BaseViewModel
import com.datn.bia.a.domain.model.entity.FavoriteEntity
import com.datn.bia.a.domain.usecase.cart.InsertCartUseCase
import com.datn.bia.a.domain.usecase.favorite.DeleteFavoriteUseCase
import com.datn.bia.a.domain.usecase.favorite.GetFavoriteUseCase
import com.datn.bia.a.domain.usecase.favorite.InsertFavoriteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductViewModel @Inject constructor(
    private val insertCartUseCase: InsertCartUseCase,
    private val getFavoriteUseCase: GetFavoriteUseCase,
    private val deleteFavoriteUseCase: DeleteFavoriteUseCase,
    private val insertFavoriteUseCase: InsertFavoriteUseCase
) : BaseViewModel() {
    private val _favoriteEntity = MutableStateFlow<FavoriteEntity?>(null)
    val favoriteEntity = _favoriteEntity.asStateFlow()

    fun addProductToCart(productId: String) = viewModelScope.launch {
        insertCartUseCase.invoke(productId).collect {

        }
    }

    fun searchProductInFavorite(id: String) = viewModelScope.launch {
        _favoriteEntity.emit(getFavoriteUseCase.invoke(id).first())
    }

    fun removeFavoriteByIdProduct(id: String) = viewModelScope.launch {
        deleteFavoriteUseCase.invoke(id)
        _favoriteEntity.emit(null)
    }

    fun addFavoriteByIdProduct(id: String) = viewModelScope.launch {
        insertFavoriteUseCase.invoke(FavoriteEntity(productId = id))
        _favoriteEntity.emit(
            FavoriteEntity(
                id = 0,
                productId = id
            )
        )
    }
}