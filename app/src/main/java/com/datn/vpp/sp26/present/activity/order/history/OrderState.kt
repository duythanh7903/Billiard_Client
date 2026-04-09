package com.datn.vpp.sp26.present.activity.order.history

import com.datn.vpp.sp26.common.UiState
import com.datn.vpp.sp26.domain.model.dto.res.ResOrderDTO
import com.datn.vpp.sp26.domain.model.entity.CommentEntity

data class OrderState(
    val uiState: UiState<List<ResOrderDTO>> = UiState.Idle,
    val listCommentCaches: List<CommentEntity> = emptyList()
) {
}