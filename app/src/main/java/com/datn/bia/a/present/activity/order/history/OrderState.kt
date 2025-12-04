package com.datn.bia.a.present.activity.order.history

import com.datn.bia.a.common.UiState
import com.datn.bia.a.domain.model.dto.res.ResOrderDTO
import com.datn.bia.a.domain.model.entity.CommentEntity

data class OrderState(
    val uiState: UiState<List<ResOrderDTO>> = UiState.Idle,
    val listCommentCaches: List<CommentEntity> = emptyList()
) {
}