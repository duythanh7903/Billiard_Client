package com.datn.vpp.sp26.present.wholesale.product

import androidx.lifecycle.viewModelScope
import com.datn.vpp.sp26.common.base.BaseViewModel
import com.datn.vpp.sp26.domain.model.dto.res.ResVariantDTO
import com.datn.vpp.sp26.domain.model.entity.FavoriteEntity
import com.datn.vpp.sp26.domain.usecase.cart.InsertCartUseCase
import com.datn.vpp.sp26.domain.usecase.comment.GetCommentUseCase
import com.datn.vpp.sp26.domain.usecase.favorite.DeleteFavoriteUseCase
import com.datn.vpp.sp26.domain.usecase.favorite.GetFavoriteUseCase
import com.datn.vpp.sp26.domain.usecase.favorite.InsertFavoriteUseCase
import com.datn.vpp.sp26.domain.usecase.feedback.GetListCacheFeedbackUseCase
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
    private val insertFavoriteUseCase: InsertFavoriteUseCase,

    getAllCommentUseCase: GetCommentUseCase,
    getAllFeedbackUseCase: GetListCacheFeedbackUseCase,
): BaseViewModel() {
    private val _favoriteEntity = MutableStateFlow<FavoriteEntity?>(null)
    val favoriteEntity = _favoriteEntity.asStateFlow()

    val stateGetAllComment = getAllFeedbackUseCase.invoke()

    init {
        launchIO {
            getAllCommentUseCase.invoke().collect { }
        }
    }

    fun addProductToCart(
        productId: String,
        variant: ResVariantDTO,
        price: Double,
    ) = viewModelScope.launch {
        insertCartUseCase.invoke(
            productId,
            variant,
            price
        ).collect {

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