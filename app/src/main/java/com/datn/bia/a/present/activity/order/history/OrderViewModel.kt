package com.datn.bia.a.present.activity.order.history

import androidx.lifecycle.viewModelScope
import com.datn.bia.a.common.UiState
import com.datn.bia.a.common.base.BaseViewModel
import com.datn.bia.a.data.storage.SharedPrefCommon
import com.datn.bia.a.domain.model.dto.req.ReqCancelOrder
import com.datn.bia.a.domain.model.dto.req.ReqCommentDTO
import com.datn.bia.a.domain.model.dto.req.ReqUpdateOrder
import com.datn.bia.a.domain.model.dto.res.ResLoginUserDTO
import com.datn.bia.a.domain.model.dto.res.ResOrderDTO
import com.datn.bia.a.domain.model.dto.res.ResUpdateOrder
import com.datn.bia.a.domain.model.entity.CommentEntity
import com.datn.bia.a.domain.usecase.comment.CacheCommentUseCase
import com.datn.bia.a.domain.usecase.comment.CreateCommentUseCase
import com.datn.bia.a.domain.usecase.comment.GetAllCommentByIdUseCase
import com.datn.bia.a.domain.usecase.order.CancelOrderUseCase
import com.datn.bia.a.domain.usecase.order.GetAllOrderByIdUseCase
import com.datn.bia.a.domain.usecase.order.UpdateOrderUseCase
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class OrderViewModel @Inject constructor(
    getAllOrderByIdUseCase: GetAllOrderByIdUseCase,
    getAllCommentByIdUserUseCase: GetAllCommentByIdUseCase,
    private val updateOrderUseCase: UpdateOrderUseCase,
    private val cacheCommentUseCase: CacheCommentUseCase,
    private val createCommentUseCase: CreateCommentUseCase,
    private val cancelOrderUseCase: CancelOrderUseCase,
) : BaseViewModel() {
    private val _stateComment = MutableStateFlow<UiState<Any>>(UiState.Idle)
    val stateComment = _stateComment.asStateFlow()


    private val _listOrder = MutableStateFlow<List<ResOrderDTO>>(emptyList())
    val listOrder = _listOrder.asStateFlow()

    private val _uiStateUpdate: MutableStateFlow<UiState<ResUpdateOrder>> =
        MutableStateFlow(UiState.Idle)
    val uiStateUpdate = _uiStateUpdate.asStateFlow()

    private val idUserCurrent =
        Gson().fromJson(SharedPrefCommon.jsonAcc, ResLoginUserDTO::class.java)?.user?.id ?: ""

    private val _state = MutableStateFlow(OrderState())
    val state = combine(
        _state,
        getAllOrderByIdUseCase.invoke(idUserCurrent),
        getAllCommentByIdUserUseCase.invoke(idUserCurrent)
    ) { state, uiState, commentsCache ->
        state.copy(
            uiState = uiState,
            listCommentCaches = commentsCache
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(
            stopTimeoutMillis = 5_000,
        ), initialValue = OrderState()
    )

    fun changeStateToIdle() {
        _state.value = _state.value.copy(
            uiState = UiState.Idle
        )
    }

    fun updateOrderUseCase(
        orderId: String,
        status: String
    ) = launchIO {
        updateOrderUseCase.invoke(
            orderId, ReqUpdateOrder(status)
        ).collect { uiState ->
            _uiStateUpdate.value = uiState
        }
    }

    fun cancelOrderUseCase(
        orderId: String,
        status: String,
        reason: String
    ) = launchIO {
        cancelOrderUseCase.invoke(
            orderId,
            ReqCancelOrder(
                reason, status
            )
        ).collect { uiState ->
            _uiStateUpdate.value = uiState
        }
    }

    fun changeStateUpdateToIdle() {
        _uiStateUpdate.value = UiState.Idle
    }

    fun cacheListOrder(listOrder: List<ResOrderDTO>) {
        _listOrder.value = listOrder.toMutableList()
    }

    fun postComment(
        orderId: String,
        productIds: List<String>,
        stars: Int,
        comment: String
    ) = launchIO {
        createCommentUseCase.invoke(
            ReqCommentDTO(
                userId = idUserCurrent,
                productId = productIds,
                content = comment,
                rating = stars
            )
        ).collect { uiState ->
            _stateComment.value = uiState
            when (uiState) {
                is UiState.Error -> {}
                UiState.Idle -> {}
                UiState.Loading -> {}
                is UiState.Success<*> -> {
                    cacheCommentUseCase.invoke(
                        CommentEntity(
                            id = 0L,
                            orderId = orderId,
                            userId = idUserCurrent
                        )
                    ).collect {

                    }
                }
            }
        }
    }

    fun resetStateComment() {
        _stateComment.value = UiState.Idle
    }
}