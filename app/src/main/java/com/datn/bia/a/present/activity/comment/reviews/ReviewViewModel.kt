package com.datn.bia.a.present.activity.comment.reviews

import androidx.lifecycle.viewModelScope
import com.datn.bia.a.common.base.BaseViewModel
import com.datn.bia.a.domain.usecase.comment.GetCommentUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class ReviewViewModel @Inject constructor(
    getAllCommentUseCase: GetCommentUseCase
) : BaseViewModel() {

    private val _state = MutableStateFlow(ReviewState())
    val state = combine(
        _state,
        getAllCommentUseCase.invoke()
    ) { state, uiState ->
        state.copy(
            uiState = uiState
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(
            stopTimeoutMillis = 5_000
        ), initialValue = ReviewState()
    )
}